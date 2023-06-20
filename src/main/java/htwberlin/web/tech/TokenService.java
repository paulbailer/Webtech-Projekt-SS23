package htwberlin.web.tech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    TokenRepository repo;

    public Token save(Token token) {
        return repo.save(token);
    }

    public Token get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException());
    }
}
