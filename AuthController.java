import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        // Here, you would authenticate the user (check username/password)
        if ("admin".equals(user.getUsername()) && "password".equals(user.getPassword())) {
            return "Authentication successful!";
        }
        return "Authentication failed!";
    }
}
