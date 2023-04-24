package htwberlin.web.tech;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    @GetMapping("/greeting/welcome/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        return "hello, " + name;
    }

    @GetMapping("/")
    public String root() {
        return "hello";
    }
}