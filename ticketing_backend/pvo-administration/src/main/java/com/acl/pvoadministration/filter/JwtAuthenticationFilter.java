package com.acl.pvoadministration.filter;


import com.acl.pvoadministration.security.JwtServices;
import com.acl.pvoadministration.security.impl.UserServiceImpl;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServices jwtServices;
    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;
        String password = null;
        String jwtokenvalue = null;

        log.info("contenu autorisation {}", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            log.info("contenu autheader {} ", authHeader);

            jwt = authHeader.substring(7);
            jwtokenvalue = jwtServices.getUserIdFromJWT(jwt);
            String[] values = jwtokenvalue.split("\\.");
            username = values[0];
            password = values[1];
        }
        log.info("contenu etape 2: ");
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("username {} ", username);
            UserDetails userDetails = userService.loadUserByUsername(username);
            log.info("userDetails : {}", userDetails.getUsername());
            if (jwtServices.isValidToken(jwt)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                log.info("valid token");
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        log.info("contenu etape 3: ");
        filterChain.doFilter(request, response);
    }
}
