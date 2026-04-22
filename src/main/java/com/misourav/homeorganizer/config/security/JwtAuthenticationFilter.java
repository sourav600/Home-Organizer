package com.misourav.homeorganizer.config.security;

import com.misourav.homeorganizer.adapter.out.security.JwtTokenProviderAdapter;
import com.misourav.homeorganizer.application.port.out.TokenProvider;
import com.misourav.homeorganizer.application.service.PermissionResolver;
import com.misourav.homeorganizer.domain.model.PermissionCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Inbound security adapter. Reads the bearer token, asks the TokenProvider port to
 * parse it, resolves effective permissions via the domain role hierarchy, and populates
 * Spring Security's context with permissions as GrantedAuthorities.
 * Controllers then just use @PreAuthorize("hasAuthority('EXPENSE_DELETE')") etc.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final PermissionResolver permissionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        String token = extractToken(request);
        if (!StringUtils.hasText(token)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            TokenProvider.ParsedToken parsed = tokenProvider.parse(token);

            Set<PermissionCode> perms = permissionResolver.resolveByRoleCode(parsed.role());

            List<SimpleGrantedAuthority> authorities = perms.stream()
                    .map(Enum::name)
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            AuthenticatedUser principal =
                    new AuthenticatedUser(parsed.userId(), parsed.role(), parsed.householdId());

            var auth = new UsernamePasswordAuthenticationToken(principal, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (JwtTokenProviderAdapter.InvalidTokenException ignored) {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length()).trim();
        }
        return null;
    }
}
