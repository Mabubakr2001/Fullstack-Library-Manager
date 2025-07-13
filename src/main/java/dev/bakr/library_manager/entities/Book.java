package dev.bakr.library_manager.entities;

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

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id")
    private Publisher publisher;

    public Book() {
    }

    public Book(Integer id,
            String title,
            String isbn,
            Integer pagesCount,
            String imageLink,
            LocalDate publishedOn,
            String status,
            Author author,
            Category category,
            Publisher publisher) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.pagesCount = pagesCount;
        this.imageLink = imageLink;
        this.publishedOn = publishedOn;
        this.status = status;
        this.author = author;
        this.category = category;
        this.publisher = publisher;
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

    public String getImageLink() {
        return imageLink;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public String getStatus() {
        return status;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public Publisher getPublisher() {
        return publisher;
    }
}
