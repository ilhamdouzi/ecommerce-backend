package com.app.ecommerce_backend.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter implements Filter {
    private static final String CORRELATION_ID_KEY = "correlationId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // Génération d'un identifiant unique pour chaque requête
            String correlationId = UUID.randomUUID().toString();
            MDC.put(CORRELATION_ID_KEY, correlationId);

            // Passe la requête au filtre suivant
            chain.doFilter(request, response);
        } finally {
            // Nettoie le MDC pour éviter les fuites entre requêtes
            MDC.remove(CORRELATION_ID_KEY);
        }
    }
}
