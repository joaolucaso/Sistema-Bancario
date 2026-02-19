package com.banco.infrastructure.security.filter;

import com.banco.infrastructure.security.service.JwtService;
import com.banco.infrastructure.security.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final SecurityService securityService;

    public JwtFilterRequest(JwtService jwtService,
                            SecurityService securityService) {
        this.jwtService = jwtService;
        this.securityService = securityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null &&
                authorizationHeader.startsWith("Bearer ")) {

            jwtToken = authorizationHeader.substring(7);

            try {
                username = jwtService.extractNome(jwtToken);
            } catch (Exception ex) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    securityService.loadUserByUsername(username);

            if (jwtService.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
