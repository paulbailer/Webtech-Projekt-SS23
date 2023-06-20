package htwberlin.web.tech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class TokenController {

    @Autowired
    TokenService service;
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http:localhost:8080/api/get-user-code/");
    private String code = "";

    private static final SpotifyApi spotifyApi  = new SpotifyApi.Builder()
            .setClientId(System.getenv("client_id"))
            .setClientSecret(System.getenv("client_secret"))
            .setRedirectUri(redirectUri)
            .build();

    @PostMapping("/token")
    public Token createToken(@RequestBody Token token) {
        return service.save(token);
    }
}
