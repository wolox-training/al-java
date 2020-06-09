package wolox.training.models;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
@ApiModel(description = "Books for users")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ")
    @SequenceGenerator(name = "BOOK_SEQ", sequenceName = "BOOK_SEQ")
    private long id;

    @ApiModelProperty(notes = "Book's genre")
    @Column()
    private String genre;

    @ApiModelProperty(notes = "Book's author", required = true)
    @Column(nullable = false)
    private String author;

    @ApiModelProperty(notes = "Book's image", required = true)
    @Column(nullable = false)
    private String image;

    @ApiModelProperty(notes = "Book's title", required = true)
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(notes = "Book's subtitle", required = true)
    @Column(nullable = false)
    private String subtitle;

    @ApiModelProperty(notes = "Book's publisher", required = true)
    @Column(nullable = false)
    private String publisher;

    @ApiModelProperty(notes = "Book's year", required = true)
    @Column(nullable = false)
    private String year;

    @ApiModelProperty(notes = "Book's pages", required = true)
    @Column(nullable = false)
    private Integer pages;

    @ApiModelProperty(notes = "Book's isbn", required = true)
    @Column(nullable = false, unique = true)
    private String isbn;

    @ManyToMany(mappedBy = "books")
    private List<User> users = new ArrayList<User>();

    public Book() { }

    public long getId(){ return id; }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
