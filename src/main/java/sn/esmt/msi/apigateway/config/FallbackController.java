package sn.esmt.msi.apigateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Contrôleur de fallback.
 * Retourne une réponse dégradée quand un microservice est indisponible.
 */
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/user-service")
    public ResponseEntity<Map<String, Object>> userServiceFallback() {
        return buildFallbackResponse("user-mobility-pass-service",
                "Le service utilisateurs est temporairement indisponible.");
    }

    @GetMapping("/trip-service")
    public ResponseEntity<Map<String, Object>> tripServiceFallback() {
        return buildFallbackResponse("trip-management-service",
                "Le service de gestion des trajets est temporairement indisponible.");
    }

    @GetMapping("/pricing-service")
    public ResponseEntity<Map<String, Object>> pricingServiceFallback() {
        return buildFallbackResponse("pricing-discount-service",
                "Le service de tarification est indisponible. Tarif standard appliqué.");
    }

    @GetMapping("/billing-service")
    public ResponseEntity<Map<String, Object>> billingServiceFallback() {
        return buildFallbackResponse("billing-service",
                "Le service de facturation est temporairement indisponible.");
    }

    @GetMapping("/notification-service")
    public ResponseEntity<Map<String, Object>> notificationServiceFallback() {
        return buildFallbackResponse("notification-service",
                "Le service de notifications est temporairement indisponible.");
    }

    private ResponseEntity<Map<String, Object>> buildFallbackResponse(String service, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("service", service);
        response.put("status", "SERVICE_UNAVAILABLE");
        response.put("message", message);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}