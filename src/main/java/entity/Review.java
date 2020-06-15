package entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "review")
public class Review implements Serializable {
    private static final long serialVersionUID = -3227695145596672647L;

    private String reviewContent;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookid")
    private Book reviewBook;
    
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private User reviewUser;

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Book getReviewBook() {
        return reviewBook;
    }

    public void setReviewBook(Book reviewBook) {
        this.reviewBook = reviewBook;
    }

    public User getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(User reviewUser) {
        this.reviewUser = reviewUser;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reviewBook == null) ? 0 : reviewBook.hashCode());
		result = prime * result + ((reviewUser == null) ? 0 : reviewUser.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (reviewBook == null) {
			if (other.reviewBook != null)
				return false;
		} else if (!reviewBook.equals(other.reviewBook))
			return false;
		if (reviewUser == null) {
			if (other.reviewUser != null)
				return false;
		} else if (!reviewUser.equals(other.reviewUser))
			return false;
		return true;
	}

	public Review(String reviewContent, Book reviewBook, User reviewUser) {
		this.reviewContent = reviewContent;
		this.reviewBook = reviewBook;
		this.reviewUser = reviewUser;
	}

	public Review() {
	}

}