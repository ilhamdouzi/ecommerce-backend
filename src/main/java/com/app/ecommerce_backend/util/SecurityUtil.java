package com.app.ecommerce_backend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public final class SecurityUtil {
    private static final String ADMIN_EMAIL = "admin@admin.com";

    private SecurityUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkAdminAccess() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            log.warn("No authentication found in SecurityContext.");
            throw new AccessDeniedException("Access denied: user is not authenticated.");
        }
        log.debug("Authenticated user: {}", auth.getName());

        if (!ADMIN_EMAIL.equals(auth.getName())) {
            log.warn("Access denied for user: {}. Only admin is allowed.", auth.getName());
            throw new AccessDeniedException("Access denied: Only admin@admin.com can perform this operation.");
        }
        log.debug("Admin access verified for user: {}", auth.getName());
    }
}
