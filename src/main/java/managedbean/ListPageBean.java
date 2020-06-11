package managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ejb.DatabaseService;
import entity.*;

@Named("list")
@SessionScoped
public class ListPageBean extends BaseBean {
    private static final long serialVersionUID = -7900371187282104730L;
    @EJB
    DatabaseService db;
    private List<Genre> selectedFilter;
    private List<Genre> filters;
    private List<Book> books;
    private boolean isRefreshed;
    private String searchTerm;
    private String searchField;
    private String searchResultTerm;

    // ------------------------------------------CONSTRUCTOR------------------------------------------
    public ListPageBean() {
        isRefreshed = false;
        books = new ArrayList<Book>();
        selectedFilter = new ArrayList<Genre>();
    }

    @PostConstruct
    public void loadGenres() {
        this.filters = db.getAllGenre();
    }

    // ------------------------------------------PAGE_FUNCTION------------------------------------------

    public String setSelectedBook(int bookId) {
        Book b = db.getBookById(bookId);
        System.out.println("LIST SELECTED BOOK: " + b.getTitle());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedBook", b);
        return "detail.xhtml?faces-redirect=true";
    }

    public String searchBook() { // Search for book by title or author
        books = new ArrayList<Book>();
        if (searchTerm.isEmpty()) {
            books = this.db.getAllBooks();
        } else {
            books = this.db.getBooks(searchField, searchTerm);
        }
        searchResultTerm = searchTerm;
        searchTerm = "";
        return "list.xhtml?faces-redirect=true";
    }

    public String searchByFilter() { // Apply filter to result list
        if (selectedFilter.size() != 0) {
            for (Genre filterOption : selectedFilter) {
                searchTerm = searchResultTerm;
                searchBook();

                System.out.println("CURRENT NUMBER OF BOOKS: " + books.size());

                BookCollection temp = this.db.getCollectionByGenre(filterOption);
                System.out.println("GETTING COLLECTION FOR FILTERING: " + temp.getCollectionName() + " NUM OF BOOKS: "
                        + temp.getCollectionBooks().size());
                filterBookList(temp.getCollectionBooks());
                System.out.println("UPDATED NUMBER OF BOOKS: " + books.size());
            }
        } else {
            searchTerm = searchResultTerm;
            searchBook();
        }

        return "list.xhtml?faces-redirect=true";
    }

    // ------------------------------------------ACCESSORS------------------------------------------
    public List<Genre> getSelectedFilter() {
        return selectedFilter;
    }

    public void setSelectedFilter(List<Genre> selectedFilter) {
        this.selectedFilter = selectedFilter;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean isRefreshed() {
        return isRefreshed;
    }

    public void setRefreshed(boolean isRefreshed) {
        this.isRefreshed = isRefreshed;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public List<Genre> getFilters() {
        return filters;
    }

    public void setFilters(List<Genre> filters) {
        this.filters = filters;
    }

    public String getSearchResultTerm() {
        return searchResultTerm;
    }

    public void setSearchResultTerm(String searchResultTerm) {
        this.searchResultTerm = searchResultTerm;
    }

    // ------------------------------------------PRIVATE_METHOD------------------------------------------
    private void filterBookList(List<Book> list) { // Filter result list
        if (books.size() < 1 || books == null) {
            books = list;
        } else {
            List<Book> temp = new ArrayList<Book>();
            for (Book tempBook : list) {
                for (Book b : books) {
                    if (b.equals(tempBook)) {
                        temp.add(b);
                        break;
                    } else {
                        continue;
                    }
                }
            }
            books = temp;
        }
    }

}