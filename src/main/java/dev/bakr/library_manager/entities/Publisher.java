package dev.bakr.library_manager.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id", updatable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @Column(name = "website")
    private String website;

    @Column(name = "contact_email")
    private String contactEmail;

    public Publisher() {
    }

    public Publisher(Integer id,
            String name,
            String country,
            Integer foundedYear,
            String website,
            String contactEmail) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.foundedYear = foundedYear;
        this.website = website;
        this.contactEmail = contactEmail;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", foundedYear=" + foundedYear +
                ", website='" + website + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
