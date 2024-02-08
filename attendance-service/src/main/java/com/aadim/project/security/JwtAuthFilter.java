package com.aadim.project.security;

import com.aadim.project.repository.TokenRepository;
import com.aadim.project.service.impl.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{

    private final JwtService jwtService;

    private final UserDetailServiceImpl userDetailServiceImpl;

    private final TokenRepository tokenRepository;
    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);
            var isTokenValid = tokenRepository.findByToken(token)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (jwtService.validateToken(token, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                // Token is invalid or expired
                if (isTokenExpired(token)) {
                    // Handle expired token
                    throw new RuntimeException("Token Expired. Please login again.");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(Date.from(Instant.now()));
        } catch (ExpiredJwtException e) {
            return true; // Token is expired
        }
    }

}
