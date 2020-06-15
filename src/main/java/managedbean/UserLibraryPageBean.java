package managedbean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.*;

@Named
@ViewScoped
public class UserLibraryPageBean extends BaseBean {
    private static final long serialVersionUID = 5324829994135860307L;
    private BookCollection selectedCollection;
    private List<Book> bookList;
    private List<Book> deletingBooks;
    @Inject
    private UserPageBean userPage;

    // --------------------------------------CONSTRUCTOR--------------------------------------
    public UserLibraryPageBean() {
        selectedCollection = new BookCollection();
        bookList = new ArrayList<Book>();
        deletingBooks = new ArrayList<Book>();
    }

    // --------------------------------------BEAN_FUNCTION--------------------------------------
    @PostConstruct
    public void initialize() {
        selectedCollection = userPage.getSelectedCollection();
        if (selectedCollection.getCollectionBooks() != null) {
            bookList = selectedCollection.getCollectionBooks();
        }
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book book1, Book book2) {
                return book1.getTitle().compareTo(book2.getTitle());
            }
        });
    }

    public String viewDeletingBooks() {
        for (Book book : deletingBooks) {
            if (bookList.contains(book))
                System.out.println("DELETING BOOK: " + book.getTitle());
        }
        bookList.removeAll(deletingBooks);
        for (Book book : bookList) {
            System.out.println("REMAINING BOOKS: " + book.getTitle());
        }
        return userPage.modifyLibrary(selectedCollection, deletingBooks);
    }

    // --------------------------------------ACCESSORS--------------------------------------

    public BookCollection getSelectedCollection() {
        return selectedCollection;
    }

    public void setSelectedCollection(BookCollection selectedCollection) {
        this.selectedCollection = selectedCollection;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getDeletingBooks() {
        return deletingBooks;
    }

    public void setDeletingBooks(List<Book> deletingBooks) {
        this.deletingBooks = deletingBooks;
    }

}