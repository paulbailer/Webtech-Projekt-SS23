package htwberlin.web.tech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}