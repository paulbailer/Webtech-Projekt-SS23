package htwberlin.web.tech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    PlaylistRepository repo;

    public Playlist save(Playlist playlist) {
        return repo.save(playlist);
    }

    public Playlist get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException());
    }

    public List<Playlist> getAll() {
        Iterable<Playlist> iterator = repo.findAll();
        List<Playlist> playlists = new ArrayList<Playlist>();
        for (Playlist playlist : iterator)  playlists.add(playlist);
        return playlists;
    }
}