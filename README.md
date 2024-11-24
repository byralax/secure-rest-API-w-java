# secure-rest-API-w-java
Here’s how we can approach this:

### 1. **Set up Spring Boot Project:**
   You can quickly set up a Spring Boot project by going to [Spring Initializr](https://start.spring.io/) and selecting the following dependencies:
   - Spring Web
   - Spring Security
   - Spring Data JPA (if you want to interact with a database)
   - H2 Database (for in-memory database, or you can use another DB like MySQL)

   Once you've configured and generated the project, you can download the `.zip` file and import it into your IDE (IntelliJ IDEA, Eclipse, etc.).

### 2. **Create User Model and Repository:**
   We will create a simple `User` model and repository to simulate user data and authentication.

   **User Model:**
   ```java
   @Entity
   public class User {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       private String username;
       private String password;
       private String role;

       // Getters and Setters
   }
   ```

   **User Repository:**
   ```java
   import org.springframework.data.jpa.repository.JpaRepository;

   public interface UserRepository extends JpaRepository<User, Long> {
       User findByUsername(String username);
   }
   ```

### 3. **Set up Security Configuration:**
   Spring Security will be used to handle authentication and authorization.

   **Security Config Class:**
   ```java
   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;
   import org.springframework.http.HttpMethod;
   import org.springframework.security.config.annotation.web.builders.HttpSecurity;
   import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
   import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

   @Configuration
   @EnableWebSecurity
   public class SecurityConfig extends WebSecurityConfigurerAdapter {

       @Override
       protected void configure(HttpSecurity http) throws Exception {
           http.csrf().disable()  // Disable CSRF for simplicity
               .authorizeRequests()
                   .antMatchers("/auth/**").permitAll()  // Allow public access to /auth endpoints
                   .antMatchers(HttpMethod.GET, "/user/**").hasRole("USER")  // Access restricted to USER role
                   .anyRequest().authenticated()  // All other endpoints require authentication
               .and()
                   .httpBasic();  // Use basic authentication
       }
   }
   ```

### 4. **Create Authentication Endpoint (Login):**
   The `/auth/login` endpoint will handle authentication and issue a token. For simplicity, we can use HTTP Basic Authentication for now.

   **Auth Controller:**
   ```java
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
   ```

   This is a simple endpoint that checks the username and password for "admin" and "password". In a real-world scenario, you would implement JWT or OAuth for token-based authentication.

### 5. **Create Protected Endpoint:**
   Now, we’ll create a protected API that only users with the `USER` role can access.

   **User Controller:**
   ```java
   import org.springframework.web.bind.annotation.*;

   @RestController
   @RequestMapping("/user")
   public class UserController {

       @GetMapping("/profile")
       public String getUserProfile() {
           return "This is a user profile.";
       }
   }
   ```

### 6. **Testing the API:**
   - **Login**: To test the login, use Postman or any HTTP client to send a `POST` request to `http://localhost:8080/auth/login` with the body:
     ```json
     {
         "username": "admin",
         "password": "password"
     }
     ```

   - **Accessing Protected Endpoint**: After successful login, try accessing the protected `/user/profile` endpoint using the credentials.

### 7. **Add Database (Optional):**
   You can add a database for storing users and roles, using JPA to persist user data.

   **User Service:**
   ```java
   @Service
   public class UserService {
       @Autowired
       private UserRepository userRepository;

       public User getUserByUsername(String username) {
           return userRepository.findByUsername(username);
       }

       // Method for saving users to the database can also be added
   }
   ```

   - Add the `application.properties` for database connection:
   ```properties
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=password
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   ```

### 8. **Security Enhancements (JWT Authentication - Optional):**
   - For a more secure system, you can implement JWT (JSON Web Token) authentication to generate and validate tokens rather than relying on basic authentication.
   - JWT allows for stateless authentication and is widely used in securing APIs.

