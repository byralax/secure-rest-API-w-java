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
