package wolox.training.models;

import com.google.common.base.Preconditions;
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
import wolox.training.exceptions.ErrorConstants;
import org.apache.commons.lang3.StringUtils;

@Entity
@ApiModel(description = "Books for users")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_SEQ")
    @SequenceGenerator(name = "BOOK_SEQ", sequenceName = "BOOK_SEQ")
    private long id;

    @Column()
    private String genre;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String image;

    @ApiModelProperty(notes = "Book's title")
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private Integer pages;

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
        Preconditions.checkNotNull(isbn, String.format(ErrorConstants.NOT_NULL, "isbn"));
        Preconditions.checkArgument(StringUtils.isNumeric(isbn),
            String.format(ErrorConstants.NOT_NUMERICAL_VALUES, "isbn"), isbn);
        this.isbn = isbn;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        Preconditions.checkNotNull(pages, String.format(ErrorConstants.NOT_NULL, "pages"));
        Preconditions.checkArgument(pages > 0,
            String.format(ErrorConstants.GREATER_THAN_ZERO, "pages"), pages);

        this.pages = pages;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        Preconditions.checkNotNull(year, String.format(ErrorConstants.NOT_NULL, "year"));
        Preconditions.checkArgument(StringUtils.isNumeric(year),
            String.format(ErrorConstants.NOT_NUMERICAL_VALUES, "year"), year);
        this.year = year;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        Preconditions.checkNotNull(publisher, String.format(ErrorConstants.NOT_NULL, "publisher"));
        this.publisher = publisher;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        Preconditions.checkNotNull(subtitle, String.format(ErrorConstants.NOT_NULL, "subtitle"));
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        Preconditions.checkNotNull(author, String.format(ErrorConstants.NOT_NULL, "author"));
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        Preconditions.checkNotNull(title, String.format(ErrorConstants.NOT_NULL, "title"));
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
        Preconditions.checkNotNull(image, String.format(ErrorConstants.NOT_NULL, "image"));
        this.image = image;
    }
}
