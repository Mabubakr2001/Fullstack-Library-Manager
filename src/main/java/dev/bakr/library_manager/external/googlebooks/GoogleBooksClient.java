package dev.bakr.library_manager.external.googlebooks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Service
// a class that lets us act as a client to Google Books API, even though we are technically the provider.
public class GoogleBooksClient {
    /* Acts as the internal waiter’s communication line with external restaurants (APIs). RestTemplate is a
    Spring-provided HTTP client that allows this application to send HTTP requests (GET, POST, PUT, DELETE) to other
    services or APIs — commonly used to fetch external data, like book info from the Google Books API. */
    private final RestTemplate restTemplate;
    /* The translator between JSON and Java. ObjectMapper is a Jackson-provided utility that deserializes JSON strings
    into Java objects and serializes Java objects back into JSON — allowing the app to understand and manipulate the data
    received from or sent to external APIs like Google Books. */
    private final ObjectMapper objectMapper;
    private final String API_KEY = "AIzaSyAXknYCjs5Yxk3Jy1SnvB85Mslqb998bHY";

    public GoogleBooksClient() {
        // Instantiate the rest template
        this.restTemplate = new RestTemplate();
        // Instantiate the object mapper
        this.objectMapper = new ObjectMapper();
    }

    public String fetchPublishedDateByTitle(String bookTitle) {
        try {
            // Encode the book title to make it URL-safe (e.g., replace spaces with %20)
            String encodedTitle = URLEncoder.encode(bookTitle, StandardCharsets.UTF_8);

            // Construct the full API URL with the encoded book title and your API key
            String url = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + encodedTitle + "&key=" + API_KEY;

            // Make a GET request to the API and to fetch the raw JSON response body as a String
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            /* Parse the raw JSON response string into a tree-like structure (JsonNode), allowing easy navigation
            through nested fields to extract the publishedDate */
            JsonNode root = objectMapper.readTree(response.getBody());

            /* Extract the "items" array from the root node — this array holds the book results from which we'll
            retrieve the publishedDate */
            JsonNode items = root.path("items");

            // Check if the "items" node is an array and not empty
            if (items.isArray() && !items.isEmpty()) {
                // Retrieve the publishedDate field from the first book in the search results by navigating through volumeInfo
                return items.get(0).path("volumeInfo").path("publishedDate").asText();
            }
            // If anything goes wrong (API error, invalid JSON, etc.), print the error
        } catch (Exception e) {
            System.err.println("Error fetching date from Google Books API: " + e.getMessage());
        }
        // If no publishedDate found or error happened, return null
        return null;
    }

    public LocalDate getParsedPublishedDate(String bookTitle) {
        // Call the GoogleBooksClient to get the published date as a String (could be "2020", "2020-05-10", etc.)
        String publishedDateStr = fetchPublishedDateByTitle(bookTitle);

        // If the API didn't return a date or failed, return null to avoid saving incorrect/fake dates
        if (publishedDateStr == null) {
            return null;
        }

        try {
            // Check if the published date is just a year (e.g., "2001")
            if (publishedDateStr.length() == 4) {
                // Convert the year into a full LocalDate using January 1st as default month/day
                return LocalDate.of(Integer.parseInt(publishedDateStr), 1, 1);
            } else {
                // Parse the full ISO date string (e.g., "2018-10-16") into a LocalDate
                return LocalDate.parse(publishedDateStr); // parses ISO date like 2018-10-16
            }
        } catch (Exception e) {
            // If the API didn't return a date or failed, return null to avoid saving incorrect/fake dates
            return null;
        }
    }
}
