package shared.libs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import shared.libs.utils.DateUtil;

import java.util.Date;

public class JwtService {
    private static JwtService jwtService;

    public static JwtService getInstance(){
        if (jwtService != null){
            return jwtService;
        }

        jwtService = new JwtService();
        return jwtService;
    }

    @Value("${spring.security.key}")
    private String key;

    public String generateToken(String user_id){
        return Jwts.builder()
                .claim("sub", user_id)
                .claim("exp", DateUtil.expirationTimeToken())
                .claim("iat", new Date())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public Object getClaim(String token, String name){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get(name);
    }

    public boolean validateToken(String token){
        Claims claim = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claim.getExpiration().after(new Date()) && claim.getSubject() != null;
    }
}
