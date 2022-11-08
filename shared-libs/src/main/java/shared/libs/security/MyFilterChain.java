package shared.libs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shared.libs.entity.UserSession;
import shared.libs.repository.UserSessionRepository;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MyFilterChain extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserSessionRepository userSessionRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            if (jwtService.validateToken(token)){
                String id = String.valueOf(jwtService.getClaim(token, "sub"));
                if (id != null){
                    Optional<UserSession> userSessionOptional = userSessionRepository.findById(id);

                    userSessionOptional.ifPresent(userSession -> {

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                            userSession.getUserDto(), token, userSession.getUserDto().getAuthorities());

                        // This object has requestAddress and sessionId
                        WebAuthenticationDetails details = new WebAuthenticationDetails(request);
                        authentication.setDetails(details);

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    });
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
