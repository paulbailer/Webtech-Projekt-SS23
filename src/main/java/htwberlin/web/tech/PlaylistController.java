package htwberlin.web.tech;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class PlaylistController {

    @Autowired
    PlaylistService service;

    Logger logger = LoggerFactory.getLogger(PlaylistController.class);

    @PostMapping("/playlists")
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return service.save(playlist);
    }

    @GetMapping("/playlists/{id}")
    public Playlist getPlaylist(@PathVariable String id) {
        logger.info("GET request on route things with {}", id);
        Long playlistId = Long.parseLong(id);
        return service.get(playlistId);
    }

    @GetMapping("/playlists")
    public List<Playlist> getAllPlaylists() {
        return service.getAll();
    }
    @GetMapping("/user")
    public ResponseEntity<String> getAPIUser(@RequestHeader("Authorization") String authorizationHeader){
        String spotifyUrl = "https://api.spotify.com/v1/me";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            HttpEntity<?> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(spotifyUrl, HttpMethod.GET, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            System.err.println("Error forwarding request to Spotify: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error forwarding request to Spotify");
        }
    }
    @GetMapping("/recommendations")
    public ResponseEntity<String> getAPIRecommendations(@RequestHeader("Authorization") String authorizationHeader,
                                                 @RequestParam("seed_genres") String seedGenres,
                                                 @RequestParam("limit") String limit,
                                                 @RequestParam("target_danceability") String target_danceability)

    {
        String spotifyUrl = "https://api.spotify.com/v1/recommendations";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);

            String urlWithParams = UriComponentsBuilder.fromUriString(spotifyUrl)
                    .queryParam("seed_genres", seedGenres)
                    .queryParam("limit", limit)
                    .queryParam("target_danceability", target_danceability)
                    .toUriString();

            HttpEntity<?> entity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(urlWithParams, HttpMethod.GET, entity, String.class);

            System.out.println("Received response from Spotify: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            System.err.println("Error forwarding request to Spotify: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error forwarding request to Spotify");
        }
    }
    @PostMapping("/create/playlist")
    public ResponseEntity<String> createPlaylist(@RequestHeader("Authorization") String authorizationHeader,
                                                 @RequestHeader("Content-Type") String contentType,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("user") String user) {
        String spotifyUrl = "https://api.spotify.com/v1/users/" + user + "/playlists";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            headers.set("Content-Type", contentType);

            Map<String, String> requestBody = Collections.singletonMap("name", name);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(spotifyUrl, entity, String.class);

            System.out.println("Received response from Spotify: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            System.err.println("Error forwarding request to Spotify: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error forwarding request to Spotify");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addTracks(@RequestHeader("Authorization") String authorizationHeader,
                                               @RequestHeader("Content-Type") String contentType,
                                               @RequestParam("id") String id,
                                                @RequestBody String requestBody){

        String spotifyUrl = "https://api.spotify.com/v1/playlists/" + id + "/tracks";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authorizationHeader);
            headers.set("Content-Type", contentType);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(spotifyUrl, HttpMethod.POST, entity, String.class);

            System.out.println("Received response from Spotify: " + response.getBody());
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            System.err.println("Error forwarding request to Spotify: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error forwarding request to Spotify");
        }
    }
}
