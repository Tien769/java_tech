package managedbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.*;

@Named
@ViewScoped
public class UserReviewPageBean extends BaseBean {
    private static final long serialVersionUID = -1831610155289959853L;
    private List<Review> reviews;
    @Inject
    private UserPageBean userPage;

    // --------------------------------------CONSTRUCTOR--------------------------------------

    public UserReviewPageBean() {
        reviews = new ArrayList<Review>();
    }

    // --------------------------------------BEAN_FUNCTION--------------------------------------

    @PostConstruct
    public void initialize() {
        userPage.loadReviews();
        this.reviews = userPage.getUserReviews();
    }

    public void viewReviews() {
        for (Review entry : reviews) {
            System.out.println("Review");
            System.out.println("Book: " + entry.getReviewBook().getTitle());
            System.out.println("Review: " + entry.getReviewContent());
        }
    }

    // --------------------------------------ACCESSORS--------------------------------------
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

}