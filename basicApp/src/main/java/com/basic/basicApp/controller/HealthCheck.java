package com.basic.basicApp.controller;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck implements HealthIndicator {

    @Override
    public Health health() {
        // Check database connection or any other check logic
        boolean databaseIsUp = checkDatabaseConnection();

        if (databaseIsUp) {
            return Health.up().withDetail("Database Status", "Available").build();
        } else {
            return Health.down().withDetail("Database Status", "Unavailable").build();
        }
    }

    private boolean checkDatabaseConnection() {
        return true;
    }
}