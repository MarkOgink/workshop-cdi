package nl.han.ica.oose.dea.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthCheckResourceTest {

    private static final String HEALTHY = "Up & Running";

    @Test
    void isHealthy() {
        // Arrange
        var healthCheckResource = new HealthCheckResource();

        // Act
        String healthy = healthCheckResource.healthy();

        // Assert
        Assertions.assertEquals(HEALTHY, healthy);
    }

}