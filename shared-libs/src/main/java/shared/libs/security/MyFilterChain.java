package shared.libs.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shared.libs.utils.NumberUtil;
import uz.nt.userservice.dto.UserDto;
import uz.nt.userservice.service.impl.UserDetailServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class MyFilterChain extends OncePerRequestFilter {
    private final JwtService jwtService = JwtService.getInstance();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ") && authHeader.contains(" null")){
            String token = authHeader.substring(7);
            if (jwtService.validateToken(token)){
                Integer id = NumberUtil.parseToInteger(jwtService.getClaim(token, "sub"));
                if (id != null){
                    UserDto userDto = UserDetailServiceImpl.usersMap.get(id);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDto, null, userDto.getAuthorities());

                    // This object has requestAddress and sessionId
                    WebAuthenticationDetails details = new WebAuthenticationDetails(request);
                    authentication.setDetails(details);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
