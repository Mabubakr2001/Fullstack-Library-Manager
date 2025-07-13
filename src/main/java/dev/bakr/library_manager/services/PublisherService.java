package dev.bakr.library_manager.services;

import dev.bakr.library_manager.entities.Publisher;
import dev.bakr.library_manager.repositories.PublisherRepository;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {
    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public Publisher findOrCreatePublisher(String publisherName) {
        return publisherRepository.findByName(publisherName)
                .orElseGet(() -> {
                    Publisher publisher = new Publisher();
                    publisher.setName(publisherName);
                    publisher.setCountry(fetchCountry(publisherName));
                    publisher.setFoundedYear(fetchFoundedYear(publisherName));
                    publisher.setWebsite(fetchWebsite(publisherName));
                    publisher.setContactEmail(fetchContactEmail(publisherName));
                    return publisherRepository.save(publisher);
                });
    }

    private String fetchCountry(String publisherName) {
        return "EGY";
    }

    private Integer fetchFoundedYear(String publisherName) {
        return 1000;
    }

    private String fetchWebsite(String publisherName) {
        return "Unknown";
    }

    private String fetchContactEmail(String publisherName) {
        return "Unknown";
    }
}
