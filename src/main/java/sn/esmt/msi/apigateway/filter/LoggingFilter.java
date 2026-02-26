package sn.esmt.msi.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtre global de logging (Gateway MVC - Spring Boot 4.x).
 * PRE-filter  : logge la requête entrante.
 * POST-filter : logge le statut de la réponse.
 */
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // PRE-FILTER
        logger.info("========================================");
        logger.info("[GATEWAY] Requête entrante");
        logger.info("[GATEWAY] Méthode : {}", request.getMethod());
        logger.info("[GATEWAY] URI     : {}", request.getRequestURI());

        // Laisser passer la requête
        filterChain.doFilter(request, response);

        // POST-FILTER
        logger.info("[GATEWAY] Statut réponse : {}", response.getStatus());
        logger.info("========================================");
    }
}