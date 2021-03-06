package ejb;

import entity.*;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface DatabaseService {
    public List<Book> getAllBooks();

    public List<Book> getBooks(String field, String value);

    public Book getBookById(int id);

    public List<BookCollection> getAllCollections();

    public List<BookCollection> getCollections(String name);

    public List<BookCollection> getPromotedCollections();

    public BookCollection getCollectionByGenre(Genre genre);

    public User getUserById(int id);

    public User verifyLogin(String email, String pass);

    public User createAccount(User user);

    public User updateAccount(User user);

    public Receipt getReceiptById(int id);

    public User makeReceipt(Integer userId, List<OrderDetail> details, Receipt receipt);

    public User makeReview(int userId, String reviewString, int bookId);

    public List<Genre> getAllGenre();

    public User deleteCollection(User user, BookCollection collection);

    // ADMIN METHODS

    public void create(Book book);

    public void create(BookCollection collection);

    public void update(Book book);

    public void update(BookCollection collection);

    public void delete(Book book);

    public void delete(BookCollection collection);

    public void delete(User user);

    public List<Receipt> getAllReceipts();

    public List<User> getAllUsers();
}