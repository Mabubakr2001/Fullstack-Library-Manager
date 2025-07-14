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

    @Column(name = "subtitle")
    private String subtitle;

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
            String subtitle,
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
        this.subtitle = subtitle;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(Integer pagesCount) {
        this.pagesCount = pagesCount;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
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
                ", author=" + author +
                ", category=" + category +
                ", publisher=" + publisher +
                '}';
    }
}
