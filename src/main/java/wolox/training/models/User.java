package wolox.training.models;

import com.google.common.base.Preconditions;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import wolox.training.exceptions.BookAlreadyOwnedException;
import wolox.training.exceptions.ErrorConstants;
import wolox.training.exceptions.BookNotFoundException;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name = "USER_SEQ", sequenceName = "USER_SEQ")
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Book> books = new ArrayList<Book>();

    public User(String username, String name, LocalDate birthDate) {
        setUsername(username);
        setName(name);
        setBirthDate(birthDate);
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        Preconditions.checkNotNull(username, String.format(ErrorConstants.NOT_NULL, "username"));
        Preconditions.checkArgument(!username.isEmpty(), String.format(ErrorConstants.NOT_EMPTY,"username"));
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name, String.format(ErrorConstants.NOT_NULL, "name"));
        Preconditions.checkArgument(!name.isEmpty(), String.format(ErrorConstants.NOT_EMPTY,"name"));
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        Preconditions.checkNotNull(birthDate, String.format(ErrorConstants.NOT_NULL, "birthDate"));
        Preconditions.checkArgument(LocalDate.now().isAfter(birthDate),
            String.format(ErrorConstants.NOT_NULL, "birthDate"));
        this.birthDate = birthDate;
    }

    public List<Book> getBooks() {
        return (List<Book>) Collections.unmodifiableList(books);
    }

    public void addBook(Book book) throws BookAlreadyOwnedException {
        if (books.contains(book)) {
            throw new BookAlreadyOwnedException();
        }
        this.books.add(book);
    }

    public void removeBook(Book book) throws  BookNotFoundException{
        if(!(getBooks().contains(book))){
            throw new BookNotFoundException(String.valueOf(book.getId()), "id");
        }
        books.remove(book);
    }
}
