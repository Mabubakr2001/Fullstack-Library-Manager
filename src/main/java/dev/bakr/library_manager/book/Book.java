package dev.bakr.library_manager.book;

import jakarta.persistence.*;

import java.time.LocalDate;

/* Marks this class as a database table entity, allowing JPA (via Hibernate) to map
objects as rows and create the table if enabled. */
@Entity
// Specifies the exact table name this entity maps to in the database
@Table(name = "book")
public class Book {
    // Specifies this field as the unique identifier (primary key) for the table
    @Id
    // Relies on MySQL's AUTO_INCREMENT to let the database generate ID values
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", updatable = false)
    private Integer id;
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;
    @Column(name = "pages_count")
    private Integer pagesCount;
    @Column(name = "image_link")
    private String imageLink;
    @Column(name = "published_on")
    private LocalDate publishedOn;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status;
    @Column(name = "author_id", nullable = false, updatable = false)
    private Integer authorId;
    @Column(name = "category_id", nullable = false, updatable = false)
    private Integer categoryId;
    @Column(name = "publisher_id", nullable = false, updatable = false)
    private Integer publisherId;

    public Book() {
    }

    public Book(Integer id, String title, String isbn, Integer pagesCount, String imageLink, LocalDate publishedOn, BookStatus status, Integer authorId, Integer categoryId, Integer publisherId) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.pagesCount = pagesCount;
        this.imageLink = imageLink;
        this.publishedOn = publishedOn;
        this.status = status;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.publisherId = publisherId;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getPagesCount() {
        return pagesCount;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public String getImageLink() {
        return imageLink;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pagesCount=" + pagesCount +
                ", imageLink='" + imageLink + '\'' +
                ", publishedOn=" + publishedOn +
                ", status='" + status + '\'' +
                ", authorId=" + authorId +
                ", categoryId=" + categoryId +
                ", publisherId=" + publisherId +
                '}';
    }
}
