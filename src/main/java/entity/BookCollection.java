package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "collection")
@NamedQueries({ @NamedQuery(name = "BookCollection.getAll", query = "SELECT c FROM BookCollection c"),
        @NamedQuery(name = "BookCollection.searchByName", query = "SELECT c FROM BookCollection c WHERE c.collectionName = :genre"),
        @NamedQuery(name = "BookCollection.getPremades", query = "SELECT c FROM BookCollection c WHERE c.isPromoted = true"),
        @NamedQuery(name = "BookCollection.getByGenre", query = "SELECT c FROM BookCollection c WHERE c.collectionGenre = :genre") 
    })
public class BookCollection implements Serializable {
    private static final long serialVersionUID = -5140048251055770889L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collectionid")
    private Integer collectionId;
    private String collectionName;
    private Boolean isPromoted;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "collection_book", joinColumns = @JoinColumn(name = "collectionid"), inverseJoinColumns = @JoinColumn(name = "bookid"))
    private List<Book> collectionBooks = new ArrayList<Book>();

    // Non-accessed
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "genreid", referencedColumnName = "genreid")
    private Genre collectionGenre;

    // Non-accessed
    @ManyToOne
    @JoinColumn(name = "userid")
    private User collectionUser;

    public BookCollection() {
    }

    public BookCollection(String name, boolean isPromoted, User user){
        this.collectionName = name;
        this.isPromoted = isPromoted;
        this.collectionUser = user;
    }

    public BookCollection(int id, String name) {
        this.collectionId = id;
        this.collectionName = name;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Boolean getIsPromoted() {
        return isPromoted;
    }

    public List<Book> getCollectionBooks() {
        return collectionBooks;
    }

    public void setIsPromoted(Boolean isPromoted) {
        this.isPromoted = isPromoted;
    }

    public void setCollectionBooks(List<Book> collectionBooks) {
        this.collectionBooks = collectionBooks;
    }

    public Genre getCollectionGenre() {
        return collectionGenre;
    }

    public void setCollectionGenre(Genre collectionGenre) {
        this.collectionGenre = collectionGenre;
    }

    public User getCollectionUser() {
        return collectionUser;
    }

    public void setCollectionUser(User collectionUser) {
        this.collectionUser = collectionUser;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((collectionId == null) ? 0 : collectionId.hashCode());
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
        BookCollection other = (BookCollection) obj;
        if (collectionId == null) {
            if (other.collectionId != null)
                return false;
        } else if (!collectionId.equals(other.collectionId))
            return false;
        return true;
    }

}