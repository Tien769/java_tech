package managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ejb.AccountService;
import entity.*;

@Named
@SessionScoped
public class UserPageBean extends BaseBean {
    private static final long serialVersionUID = -2813160860598505747L;
    private User user;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private int selectedCollectionId;
    private String newCollectionName;
    private List<BookCollection> userBookCollections;
    private List<Receipt> userReceipts;
    private List<Review> userReviews;
    @EJB
    private AccountService as;
    @Inject
    private ListPageBean listPageBean;

    // ------------------------------------------CONSTRUCTOR------------------------------------------

    public UserPageBean() {
        user = new User();
        updateBean();
        userBookCollections = new ArrayList<BookCollection>();
        userReceipts = new ArrayList<Receipt>();
        userReviews = new ArrayList<Review>();
    }
    // ------------------------------------------BEAN_FUNCTION------------------------------------------

    public String signIn() { // Sign in
        User user = as.authenticate(email, password);
        if (user != null) {
            System.out.println("Loging in");
            this.user = user;
            System.out.println(this.user.getUserName());
            updateBean();
            return "welcome.xhtml?faces-redirect=true";
        }
        System.out.println("USER LOGGING IN: " + this.user.getUserName());
        PrimeFaces.current().ajax().update("userCollection");
        return "fail";
    }

    public String signUp() { // Sign up
        User user = new User(this.name, this.password, this.address, this.phone, this.email);
        as.createAccount(user);
        User newUser = as.authenticate(email, password);
        if (newUser != null) {
            this.user = newUser;
            updateBean();
            return "welcome.xhtml?faces-redirect=true";
        } else {
            return "fail";
        }
    }

    public String signOut() { // Sign out
        as.logOut();
        user = null;
        updateBean();
        return "welcome.xhtml?faces-redirect=true";
    }

    public void updateAccount() { // Make changes to account
        User modifiedUser = new User(name, password, address, phone, email);
        modifiedUser.setUserCollections(this.userBookCollections);
        as.updateAccount(modifiedUser);
        user = as.getCurrentUser();
        updateBean();
    }

    public void loadReviews() {
        userReviews = user.getUserReviews();
    }

    public void loadLibrary() {
        userBookCollections = user.getUserCollections();
    }

    public void loadReceipt() {
        userReceipts = user.getUserReceipts();
    }

    public String isLoggedIn() {
        if (this.name != null) {
            return "account.xhtml?faces-redirect=true";
        } else {
            return "signin.xhtml?faces-redirect=true";
        }
    }

    public void updateBean() {
        if (user != null) {
            this.name = user.getUserName();
            this.email = user.getUserMail();
            this.password = user.getUserPassword();
            this.address = user.getUserAddress();
            this.phone = user.getUserPhone();
            loadLibrary();
            loadReceipt();
            loadReviews();
        } else {
            this.name = null;
            this.email = null;
            this.password = null;
            this.address = null;
            this.phone = null;
            this.userBookCollections = null;
            this.userReceipts = null;
            this.userReviews = null;
            System.out.println("REVIEWS IS NOW NULL");
        }
    }

    public void makeReview(String reviewString, int bookId) {
        this.user = as.makeReview(this.getUser(), reviewString, bookId);
        listPageBean.selectBook(bookId);
        updateBean();
    }

    public String addCollection() {
        this.user.getUserCollections().add(new BookCollection(this.newCollectionName, false, this.user));
        this.user = as.syncAccount(this.user);
        updateBean();
        return "account.xhtml?faces-redirect=true";
    }

    public String deleteCollection() {
        this.user.getUserCollections().remove(new BookCollection(this.selectedCollectionId, "whatever"));
        this.user = as.syncAccount(this.user);
        updateBean();
        return "account.xhtml?faces-redirect=true";
    }

    public void modifyLibrary() {
        this.user.setUserCollections(this.userBookCollections);
        this.user = as.syncAccount(this.user);
        updateBean();
    }
    // ------------------------------------------ACESSORS------------------------------------------

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        if (name.length() == 0 || name == null) {
            return "Login";
        }
        return name;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookCollection> getUserBookCollections() {
        return userBookCollections;
    }

    public void setUserBookCollections(List<BookCollection> userBookCollections) {
        this.userBookCollections = userBookCollections;
    }

    public List<Receipt> getUserReceipts() {
        return userReceipts;
    }

    public void setUserReceipts(List<Receipt> userReceipts) {
        this.userReceipts = userReceipts;
    }

    public List<Review> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<Review> userReviews) {
        this.userReviews = userReviews;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getSelectedCollection() {
        return selectedCollectionId;
    }

    public void setSelectedCollection(int selectedCollectionId) {
        this.selectedCollectionId = selectedCollectionId;
    }

    public String getNewCollectionName() {
        return newCollectionName;
    }

    public void setNewCollectionName(String newCollectionName) {
        this.newCollectionName = newCollectionName;
    }
}