package dev.bakr.library_manager.services;

import dev.bakr.library_manager.entities.Author;
import dev.bakr.library_manager.external.wikidata.WikidataClient;
import dev.bakr.library_manager.repositories.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;

@Service
public class AuthorService {
    private final WikidataClient wikidataClient;
    private final AuthorRepository authorRepository;

    public AuthorService(WikidataClient wikidataClient, AuthorRepository authorRepository) {
        this.wikidataClient = wikidataClient;
        this.authorRepository = authorRepository;
    }

    public Author findOrCreateAuthor(String authorFullName) {
        HashMap<String, String> fetchedAuthorMoreInfo = wikidataClient.fetchAuthorMoreInfo(authorFullName);
        return authorRepository.findByFullName(authorFullName)
                .orElseGet(() -> {
                    Author author = new Author();
                    author.setFullName(authorFullName);
                    author.setCountry(fetchedAuthorMoreInfo.get("country"));
                    author.setBirthDate(LocalDate.parse(fetchedAuthorMoreInfo.get("birthDate")));
                    return authorRepository.save(author);
                });
    }
}
