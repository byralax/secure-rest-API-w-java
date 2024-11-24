import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    public String getUserProfile() {
        return "This is a user profile.";
    }
}
