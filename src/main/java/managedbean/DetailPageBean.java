package managedbean;

import ejb.DatabaseService;
import entity.Book;
import entity.Review;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class DetailPageBean extends BaseBean {
    private static final long serialVersionUID = -9186255292380380072L;
    private Book selectedBook;
    private List<Review> bookReviews;
    private String userReview;
    @EJB
    private DatabaseService db;
    @Inject
    private CartPageBean cart;
    @Inject
    private UserPageBean userPageBean;

    public DetailPageBean() {
        selectedBook = new Book();
        bookReviews = new ArrayList<Review>();
    }

    public void makeReview() {
        System.out.println("USER REVIEW: " + userReview);
        userPageBean.makeReview(userReview, selectedBook.getBookId());
        userReview = null;
    }

    public String setSelectedBook(int bookId) {
        selectedBook = db.getBookById(bookId);
        cart.setRequestBookId(selectedBook.getBookId());
        this.setBookReviews(this.selectedBook.getBookReviews());
        return "detail.xhtml?faces-redirect=true";
    }

    public Book getSelectedBook() {
        return this.selectedBook;
    }

    public List<Review> getBookReviews() {
        return this.bookReviews;
    }

    public void setBookReviews(List<Review> bookReviews) {
        this.bookReviews = bookReviews;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }
}