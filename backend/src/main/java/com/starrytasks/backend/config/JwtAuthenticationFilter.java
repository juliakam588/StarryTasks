package com.starrytasks.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starrytasks.backend.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth/login") || request.getServletPath().contains("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        try {
            userEmail = jwtService.extractEmail(jwtToken);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwtToken, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

                filterChain.doFilter(request, response);
            }
        }  catch (ExpiredJwtException e) {
            handleException(response, "JWT Token expired.", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            handleException(response, e.getMessage(), HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private void handleException(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        String errorJson = String.format("{\"error\": \"Unauthorized\", \"message\": \"%s\"}", message);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(errorJson);
    }

}