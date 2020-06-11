package managedbean;

import ejb.DatabaseService;
import entity.Book;
import entity.BookCollection;
import entity.Review;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@Named
@ViewScoped
public class DetailPageBean extends BaseBean {
    private static final long serialVersionUID = -9186255292380380072L;
    private Book selectedBook;
    private List<Review> bookReviews;
    private List<BookCollection> selectedBookCollections;
    private List<BookCollection> userBookCollections;
    private String userReview;
    private String addToCollectionResult;
    private String isLoggedIn;
    @EJB
    private DatabaseService db;
    @Inject
    private CartPageBean cart;
    @Inject
    private UserPageBean userPageBean;
    @Inject
    private ListPageBean listPageBean;

    public DetailPageBean() {
        selectedBook = new Book();
        bookReviews = new ArrayList<Review>();
        userBookCollections = new ArrayList<BookCollection>();
        selectedBookCollections = new ArrayList<BookCollection>();
        addToCollectionResult = "";
    }

    @PostConstruct
    public void initialize() {
        loadBook();
        userBookCollections = userPageBean.getUserBookCollections();
    }

    // PAGE FUNCTION
    public String makeReview() {
        System.out.println("USER REVIEW: " + userReview);
        userPageBean.makeReview(userReview, selectedBook.getBookId());
        return listPageBean.setSelectedBook(this.selectedBook.getBookId());
    }

    // ACCESSORS

    public Book getSelectedBook() {
        return this.selectedBook;
    }

    public List<Review> getBookReviews() {
        return this.bookReviews;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }

    public String addBookToCollection() {
        for (BookCollection c : selectedBookCollections) {
            System.out.println("COLLECTION: " + this.userBookCollections.size() + " SELECTED"
                    + this.selectedBookCollections.size());
            if (c.getCollectionBooks().contains(this.selectedBook)) {
                addToCollectionResult += "This book is already in collection " + c.getCollectionName();
                System.out.println("BOOK ALREADY IN COLLECTION");
            } else {
                c.getCollectionBooks().add(this.selectedBook);
                System.out.println("BOOK NOT IN COLLECTION. ADDING");
            }

            for (BookCollection userCollection : this.userBookCollections) {
                if (userCollection.equals(c)) {
                    System.out.println("SAVING COLLECTION: " + userCollection.getCollectionName());
                    userCollection.setCollectionBooks(c.getCollectionBooks());
                }
            }
        }
        for (BookCollection c : this.userBookCollections) {
            System.out.println("COLLECTION: " + c.getCollectionName() + " NO.BOOKS: " + c.getCollectionBooks().size());
        }

        userPageBean.setUserBookCollections(this.userBookCollections);
        // userPageBean.updateAccount();
        userPageBean.modifyLibrary();
        listPageBean.setSelectedBook(this.selectedBook.getBookId());

        return "detail.xhtml?faces-redirect=true";
    }

    // PRIVATE METHODS

    private void loadBook() {
        this.selectedBook = (Book) FacesContext.getCurrentInstance().getExternalContext().getFlash()
                .get("selectedBook");
        System.out.println("DETAIL SELECTED BOOK ID: " + this.selectedBook.getBookId());
        cart.setRequestBookId(selectedBook.getBookId());
        this.bookReviews = this.selectedBook.getBookReviews();
    }

    public List<BookCollection> getSelectedBookCollections() {
        return selectedBookCollections;
    }

    public void setSelectedBookCollections(List<BookCollection> selectedBookCollections) {
        this.selectedBookCollections = selectedBookCollections;
    }

    public List<BookCollection> getUserBookCollections() {
        return userBookCollections;
    }

    public void setUserBookCollections(List<BookCollection> userBookCollections) {
        this.userBookCollections = userBookCollections;
    }

    public String getAddToCollectionResult() {
        return addToCollectionResult;
    }

    public void setAddToCollectionResult(String addToCollectionResult) {
        this.addToCollectionResult = addToCollectionResult;
    }

    public String getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(String isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

}