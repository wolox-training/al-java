package wolox.training.services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class passwordEncoderService {
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static String encode(String password) {
        return passwordEncoder().encode(password);
    }

    public static boolean matches(CharSequence password, String encodedPassword) {
        return passwordEncoder().matches(password, encodedPassword);
    }
}