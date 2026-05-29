package ntu.PhamGiaKhiem.TourBooking;


import ntu.PhamGiaKhiem.TourBooking.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/home","/css/**", "/js/**").permitAll()
                .requestMatchers("/admin/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/products", true)
            )
            .logout(logout -> logout
                .permitAll()
                .logoutSuccessUrl("/login?logout")
            );
        return http.build();
    }
}