package com.nikshepav.jwtauth.security;

import com.nikshepav.jwtauth.exception.UnauthorizedException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHandler jwtHandler;

    @Autowired
    public JwtAuthenticationFilter(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse, FilterChain filterChain)
        throws ServletException, IOException {
        final String token = jwtHandler.resolve(httpServletRequest);
        try {
            if (token != null && jwtHandler.validate(token)) {
                final Authentication authentication = jwtHandler.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UnauthorizedException ex) {
            SecurityContextHolder.clearContext();
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
