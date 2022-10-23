package shared.libs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shared.libs.configuration.Config;
import shared.libs.utils.NumberUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class FilterChains extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder = Config.passwordEncoder();
    private final UserDetailServiceImpl

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ") && authHeader.contains(" null")){
            String token = authHeader.substring(7);
            if (jwtService.validateToken(token)){
                Integer id = NumberUtil.parseToInteger(jwtService.getClaim(token, "sub"));
                if (id != null){

                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
