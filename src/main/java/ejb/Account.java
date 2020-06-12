package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import entity.*;

@Stateful
public class Account implements AccountService {
    @EJB
    private DatabaseService db;
    private User currentUser;

    @Override
    public User authenticate(String email, String pass) {
        User user = db.verifyLogin(email, pass);
        setCurrentUser(user);
        return user;
    }

    @Override
    public void createAccount(User user) {
        db.createAccount(user);
    }

    @Override
    public void updateAccount(User user) {
        currentUser.setUserName(user.getUserName());
        currentUser.setUserMail(user.getUserMail());
        currentUser.setUserPhone(user.getUserPhone());
        currentUser.setUserAddress(user.getUserAddress());
        currentUser.setUserPassword(user.getUserPassword());
        db.updateAccount(currentUser);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setCurrentUser(User user) {
        currentUser = user;
    }

    @Override
    public void logOut() {
        currentUser = null;
    }

    @Override
    public User makeReview(User user, String reviewString, int bookId) {
        Book book = db.getBookById(bookId);
        Review review = new Review(reviewString, book, user);
        for(Review r : user.getUserReviews()){
            if(r.getReviewUser() == user && r.getReviewBook().getBookId() == bookId){
                System.out.println("MAKING CHANGE TO PAST REVIEW");
                r.setReviewContent(reviewString);
                return db.updateAccount(user);
            }
        }
        user.getUserReviews().add(review);
        book.getBookReviews().add(review);
        db.update(book);
        return db.updateAccount(user);
    }

    @Override
    public User syncAccount(User user) {
        return db.updateAccount(user);
    }

}