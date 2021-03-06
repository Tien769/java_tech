package ejb;

import javax.ejb.Remote;

import entity.*;

@Remote
public interface AccountService {

    public User authenticate(String email, String pass);

    public void createAccount(User user);

    public void updateAccount(User user);

    public void logOut();

    public User deleteCollection(User user, BookCollection collection);

    public User getCurrentUser();

    public void setCurrentUser(User user);

    public User makeReview(User user, String reviewString, int bookId);

    public User syncAccount(User user);
}