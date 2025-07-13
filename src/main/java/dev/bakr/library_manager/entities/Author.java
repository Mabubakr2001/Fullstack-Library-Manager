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

    @Column(name = "nationality", updatable = false, nullable = false)
    private String nationality;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    public Author() {
    }

    public Author(Integer id, String fullName, String nationality, LocalDate birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.nationality = nationality;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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
                ", nationality='" + nationality + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
