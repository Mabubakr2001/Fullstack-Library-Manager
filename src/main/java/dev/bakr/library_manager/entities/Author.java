package dev.bakr.library_manager.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", updatable = false)
    private Integer id;

    @Column(name = "full_name", updatable = false, nullable = false)
    private String fullName;

    @Column(name = "country", updatable = false, nullable = false)
    private String country;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    public Author() {
    }

    public Author(Integer id, String fullName, String country, LocalDate birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.country = country;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", country='" + country + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
