package sn.esmt.msi.apigateway.config;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> notificationServiceRoute() {
        return GatewayRouterFunctions.route("notification-service")
                .route(RequestPredicates.path("/api/notifications/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("notification-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("notificationCB",
                        URI.create("forward:/fallback/notification-service")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user-service")
                .route(RequestPredicates.path("/api/users/**")
                                .or(RequestPredicates.path("/api/mobility-pass/**"))
                                .or(RequestPredicates.path("/api/auth/**")),
                        HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("user-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userCB",
                        URI.create("forward:/fallback/user-service")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> tripServiceRoute() {
        return GatewayRouterFunctions.route("trip-service")
                .route(RequestPredicates.path("/api/trips/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("trip-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("tripCB",
                        URI.create("forward:/fallback/trip-service")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> pricingServiceRoute() {
        return GatewayRouterFunctions.route("pricing-service")
                .route(RequestPredicates.path("/api/pricing/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("pricing-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("pricingCB",
                        URI.create("forward:/fallback/pricing-service")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> billingServiceRoute() {
        return GatewayRouterFunctions.route("billing-service")
                .route(RequestPredicates.path("/api/billing/**"), HandlerFunctions.http())
                .filter(LoadBalancerFilterFunctions.lb("billing-service"))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("billingCB",
                        URI.create("forward:/fallback/billing-service")))
                .build();
    }
}