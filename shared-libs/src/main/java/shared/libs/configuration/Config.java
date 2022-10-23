package shared.libs.configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Config {
    private static PasswordEncoder passwordEncoder;

    public static PasswordEncoder passwordEncoder(){
        if (passwordEncoder != null){
            return passwordEncoder;
        }
        passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder;
    }
}
