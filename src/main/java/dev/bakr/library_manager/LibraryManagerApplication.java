package dev.bakr.library_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagerApplication {
    public static void main(String[] args) {
        /* Loads Spring's application context (basically builds the entire application
        from your classes). It's like saying "Hey Spring! Here's my main application
        class (LibraryManagerApplication). Start up the whole app, scan for beans,
        build the context, and run the embedded server!"' */
        SpringApplication.run(LibraryManagerApplication.class, args);
    }
}
