package managedbean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ejb.AdminService;
import entity.*;

@Named
@SessionScoped
public class adminPageBean implements Serializable {
    private static final long serialVersionUID = -8350347902875206697L;
    @EJB
    private AdminService ads;
    private int stat;
    private List<Book> bookList;
    private List<BookCollection> collectionList;
    private List<User> userList;
    private List<Receipt> receiptList;

    // INITIALIZER
    public adminPageBean() {
    }

    public String initialize() {
        return "admin.xhtml?faces-redirect=true";
    }

    // PAGE FUNCTION
    public String showBooks() {
        bookList = ads.readAllBook();
        Collections.sort(bookList, new Comparator<Book>() {
            @Override
            public int compare(Book b1, Book b2) {
                return b1.getTitle().compareTo(b2.getTitle());
            }
        });

        stat = 1;
        return "admin.xhtml?faces-redirect=true";
    }

    public String showCollections() {
        collectionList = ads.readAllBookCollection();
        Collections.sort(collectionList, new Comparator<BookCollection>() {
            @Override
            public int compare(BookCollection c1, BookCollection c2) {
                return c1.getCollectionName().compareTo(c2.getCollectionName());
            }
        });

        stat = 2;
        return "admin.xhtml?faces-redirect=true";
    }

    public String showUsers() {
        System.out.println("GETTING ALL USERS");
        userList = ads.readAllUser();
        System.out.println("SORTING USERS BY NAME");
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getUserName().compareTo(u2.getUserName());
            }
        });
        System.out.println("USER COUNT: " + userList.size());

        stat = 3;
        return "admin.xhtml?faces-redirect=true";
    }

    public String showReceipts() throws ParseException {
        receiptList = ads.readAllReceipt();
        Collections.sort(receiptList, new Comparator<Receipt>() {
            @Override
            public int compare(Receipt r1, Receipt r2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date d1;
                    Date d2;
                    d1 = sdf.parse(r1.getDate());
                    d2 = sdf.parse(r2.getDate());
                    return d2.compareTo(d1);
                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    return 0;
                }
            }
        });
        
        stat = 4;
        return "admin.xhtml?faces-redirect=true";
    }

    // PROPERTIES
    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<BookCollection> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<BookCollection> collectionList) {
        this.collectionList = collectionList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

}