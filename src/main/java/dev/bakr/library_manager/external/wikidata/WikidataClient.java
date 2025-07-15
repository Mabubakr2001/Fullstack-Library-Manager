package dev.bakr.library_manager.external.wikidata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;

@Service
public class WikidataClient {
    private static final Logger logger = LoggerFactory.getLogger(WikidataClient.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WikidataClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public HashMap<String, String> fetchAuthorMoreInfo(String authorFullName) {
        HashMap<String, String> authorMoreInfo = new HashMap<>();

        try {
            String searchUrl = "https://www.wikidata.org/w/api.php?action=wbsearchentities&search=" +
                    URLEncoder.encode(authorFullName, StandardCharsets.UTF_8) +
                    "&language=en&format=json&type=item";

            // getting the response -> rest template help
            ResponseEntity<String> searchResponse = restTemplate.getForEntity(searchUrl, String.class);
            // converting the response into JSON -> object mapper help (jackson utility)
            JsonNode searchJson = objectMapper.readTree(searchResponse.getBody());
            /* extracting the search object from the JSON data (includes multiple people holding the same name -> like
            a Civil registry) */
            JsonNode searchResults = searchJson.path("search");

            /* guard clause that returns if the search results (collection) object is empty or if it's not an array at all
            (empty Civil registry) */
            if (!searchResults.isArray() || searchResults.isEmpty()) {
                logger.warn("No Wikidata results found for author: {}", authorFullName);
                return authorMoreInfo;
            }

            JsonNode targetedPerson = null;

            Set<String> keywordSet = Set.of("author",
                                            "writer",
                                            "professor",
                                            "psychologist",
                                            "neuroscientist",
                                            "psychiatrist",
                                            "physiologist",
                                            "novelist",
                                            "journalist",
                                            "biographer",
                                            "poet",
                                            "editor",
                                            "screenwriter",
                                            "playwright",
                                            "philosopher",
                                            "historian",
                                            "literary",
                                            "storyteller",
                                            "psychotherapist"
            );

            // loop over the search results (collection) -> search and looking over the civil registry
            for (JsonNode person : searchResults) {
                // get the description of the person to look over it
                String description = person.path("description").asText("").toLowerCase();

                // Split the description into individual words (tokens)
                String[] words = description.split("\\W+"); // non-word characters as delimiters

                for (String word : words) {
                    // check if the description holder any keyword from these
                    if (keywordSet.contains(word)) {
                        // if yes -> make it the targeted person
                        targetedPerson = person;
                        // Break out of the loop cuz a match was found (i.e., targetedPerson is no longer null).
                        break;
                    }
                }
            }
            // return if didn't find any person with this description -> the targeted author isn't included inside the registry
            if (targetedPerson == null) {
                logger.warn("No reliable match found for author: {}", authorFullName);
                return authorMoreInfo; // empty map — no birthDate or nationality
            }

            // get the Wikidata Entity ID of that person (the correct one) to make a more specific request about him
            String wikidataEntityId = targetedPerson.path("id").asText(); // e.g., "Q47528"

            // make the request about that person (an entity)
            String entityUrl = "https://www.wikidata.org/wiki/Special:EntityData/" + wikidataEntityId + ".json";

            ResponseEntity<String> entityResponse = restTemplate.getForEntity(entityUrl, String.class);

            JsonNode entityJson = objectMapper.readTree(entityResponse.getBody());

            /* Navigate the JSON tree (look for a specific info about that author) to extract the "claims" node, which
            holds structured data like birthdate (P569), nationality (P27), etc., for the given entity (author). */
            JsonNode entity = entityJson.path("entities").path(wikidataEntityId).path("claims");

            // Access the "P569" property from the claims node — "P569" is the Wikidata property ID for date of birth.
            JsonNode birthDateNode = entity.path("P569");

            // Check if the birthDateNode is a non-empty array (Wikidata stores claims as arrays)
            if (birthDateNode.isArray() && !birthDateNode.isEmpty()) {
                // Navigate through the nested structure to extract the raw birthdate (e.g., "+1949-06-25T00:00:00Z")
                String rawBirthDate = birthDateNode.get(0).path("mainsnak")
                        .path("datavalue").path("value").path("time").asText();
                // Clean the date string and store only the date part (YYYY-MM-DD) in the result map
                authorMoreInfo.put("birthDate", rawBirthDate.substring(1, 11)); // e.g., 1949-06-25
            }


            // Access the "P27" property from the claims node — "P27" is the Wikidata property ID for country.
            JsonNode countryNode = entity.path("P27");

            // Check if the countryNode is a non-empty array (Wikidata stores claims as arrays)
            if (countryNode.isArray() && !countryNode.isEmpty()) {
                /* Extract the Wikidata property ID of the country entity from the first result. This ID will be
                used to make another API request to get the human-readable label (e.g., "United States") */
                String countryQid = countryNode.get(0).path("mainsnak")
                        .path("datavalue").path("value").path("id").asText();

                // Construct the URL to fetch detailed data for the country Q-ID from Wikidata
                String labelUrl = "https://www.wikidata.org/wiki/Special:EntityData/" + countryQid + ".json";

                ResponseEntity<String> labelResponse = restTemplate.getForEntity(labelUrl, String.class);
                JsonNode labelJson = objectMapper.readTree(labelResponse.getBody());

                // Navigate through the JSON to extract the English label (e.g., "United States") for that country entity
                String countryLabel = labelJson.path("entities")
                        .path(countryQid)
                        .path("labels")
                        .path("en")
                        .path("value")
                        .asText();

                authorMoreInfo.put("country", countryLabel);
            }

        } catch (Exception e) {
            logger.error("Error fetching author info from Wikidata", e);
        }

        return authorMoreInfo;
    }
}

