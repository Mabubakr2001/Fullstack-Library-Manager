package dev.bakr.library_manager.external.googlebooks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

@Service
// a class that lets us act as a client to Google Books API, even though we are technically the provider.
public class GoogleBooksClient {
    /* Logger instance used for recording runtime information, warnings, and errors specific to the GoogleBooksClient class.
    Helps in debugging and monitoring application behavior by writing logs to the console or log files. */
    private static final Logger logger = LoggerFactory.getLogger(GoogleBooksClient.class);
    /* Acts as the internal waiter’s communication line with external restaurants (APIs). RestTemplate is a
    Spring-provided HTTP client that allows this application to send HTTP requests (GET, POST, PUT, DELETE) to other
    services or APIs — commonly used to fetch external data, like book info from the Google Books API. */
    private final RestTemplate restTemplate;
    /* The translator between JSON and Java. ObjectMapper is a Jackson-provided utility that deserializes JSON strings
    into Java objects and serializes Java objects back into JSON — allowing the app to understand and manipulate the data
    received from or sent to external APIs like Google Books. */
    private final ObjectMapper objectMapper;

    /* Injects the Google Books API key from application.yml using Spring's @Value annotation.
    This allows externalizing configuration and avoids hardcoding secrets in the codebase. */
    @Value("${google.books.api.key}")
    private String API_KEY;

    @Autowired
    public GoogleBooksClient() {
        // Instantiate the rest template
        this.restTemplate = new RestTemplate();
        // Instantiate the object mapper
        this.objectMapper = new ObjectMapper();
    }

    private static LocalDate polishDate(String parsedDate) {
        LocalDate polishedDate;
        // Check if the published date is just a year (e.g., "2001")
        if (parsedDate.length() == 4) {
            // Converts a year-only date string (e.g., "2001") into a full LocalDate using January 1st as the default month and day
            polishedDate = LocalDate.of(Integer.parseInt(parsedDate), 1, 1);
        } else {
            // Parse the full ISO date string (e.g., "2018-10-16") into a LocalDate
            polishedDate = LocalDate.parse(parsedDate); // parses ISO date like 2018-10-16
        }
        return polishedDate;
    }

    public HashMap<String, String> fetchBookMoreInfoByTitle(String bookTitle, Set<String> neededFields) {
        // Create an empty hashmap (object) to store the extracted values (e.g., publishedDate, subtitle, etc.)
        HashMap<String, String> bookMoreInfo = new HashMap<>();

        try {
            // Encode the book title to make it URL-safe (e.g., replace spaces with %20)
            String encodedTitle = URLEncoder.encode(bookTitle, StandardCharsets.UTF_8);

            // Construct the full API URL with the encoded book title and your API key
            String searchUrl = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + encodedTitle + "&key=" + API_KEY;

            // Make a GET request to the API and to fetch the raw JSON response body as a String
            ResponseEntity<String> searchResponse = restTemplate.getForEntity(searchUrl, String.class);

            /* Parse the raw JSON response string into a tree-like structure (JsonNode), allowing easy navigation
            through nested fields to extract the needed info */
            JsonNode searchJson = objectMapper.readTree(searchResponse.getBody());

            /* Extract the "items" array from the root node — this array holds the book results from which we'll
            retrieve the needed info */
            JsonNode searchResults = searchJson.path("items");

            //
            if (!searchResults.isArray() || searchResults.isEmpty()) {
                logger.warn("No Google Books results found for book: {}", bookTitle);
                return bookMoreInfo;
            }

            // navigates into the JSON tree to get the book metadata object for the first returned book.
            JsonNode volumeInfo = searchResults.get(0).path("volumeInfo");

            // Loop through each field name requested (e.g., "publishedDate", "subtitle", etc.)
            for (String field : neededFields) {
                // Extract the value of the current field from the volumeInfo node
                String value = volumeInfo.path(field).asText();
                // add the key (field) to the hashmap and its value
                bookMoreInfo.put(field, value);
            }

            // Return the list of extracted values as a result
            return bookMoreInfo;

            // If anything goes wrong (API error, invalid JSON, etc.), log an error
        } catch (Exception e) {
            logger.error("Error fetching data from Google Books API {}", e.getMessage());
        }
        return bookMoreInfo;
    }

    public HashMap<String, String> getBookMoreInfo(String bookTitle, Set<String> neededInfo) {
        HashMap<String, String> parsedBookMoreInfo = fetchBookMoreInfoByTitle(bookTitle, neededInfo);

        if (parsedBookMoreInfo.isEmpty()) {
            return null;
        }

        try {
            var parsedDate = parsedBookMoreInfo.get("publishedDate");
            LocalDate polishedDate = polishDate(parsedDate);
            parsedBookMoreInfo.replace("publishedDate", String.valueOf(polishedDate));
            return parsedBookMoreInfo;
        } catch (Exception e) {
            // If the API didn't return a date or failed, return null to avoid saving incorrect/fake dates
            return null;
        }
    }
}
