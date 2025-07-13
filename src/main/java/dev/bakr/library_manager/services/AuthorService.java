package dev.bakr.library_manager.services;

import dev.bakr.library_manager.entities.Author;
import dev.bakr.library_manager.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findOrCreateAuthor(String authorFullName) {
        return authorRepository.findByFullName(authorFullName)
                .orElseGet(() -> {
                    Author author = new Author();
                    author.setFullName(authorFullName);
                    author.setNationality(fetchNationality(authorFullName));
                    author.setBirthDate(fetchBirthDate(authorFullName));
                    return authorRepository.save(author);
                });
    }

    private String fetchNationality(String authorFullName) {
        // Placeholder for external API or hardcoded logic
        return "Unknown";
    }

    private LocalDate fetchBirthDate(String authorFullName) {
        // Placeholder
        return LocalDate.of(1900, 1, 1);
    }
}
