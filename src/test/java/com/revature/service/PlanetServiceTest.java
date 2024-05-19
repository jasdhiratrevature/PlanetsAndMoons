package com.revature.service;

import com.revature.exceptions.PlanetFailException;
import com.revature.models.Planet;
import com.revature.repository.PlanetDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlanetServiceTest {

    @Mock
    private PlanetDao planetDao;

    @InjectMocks
    private PlanetService planetService;

    private Planet planet;

    @BeforeEach
    public void setUp() {
        planet = new Planet();
    }

    @Test
    @DisplayName("Add Planet::Valid - Name with Leading Uppercase")
    @Order(1)
    public void testCreatePlanetWithLeadingUppercase() {
        // Arrange
        int ownerId = 1;
        planet.setName("Earth");
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth")).thenReturn(null);
        Mockito.when(planetDao.createPlanet(planet)).thenReturn(planet);

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNotNull(createdPlanet);
        Assertions.assertEquals("earth", createdPlanet.getName());
    }

    @Test
    @DisplayName("Add Planet::Valid - Name with All Uppercase")
    @Order(2)
    public void testCreatePlanetWithAllUppercase() {
        // Arrange
        int ownerId = 1;
        planet.setName("EARTH");
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth")).thenReturn(null);
        Mockito.when(planetDao.createPlanet(planet)).thenReturn(planet);

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNotNull(createdPlanet);
        Assertions.assertEquals("earth", createdPlanet.getName());
    }

    @Test
    @DisplayName("Add Planet::Valid - Name with Leading and Trailing Spaces")
    @Order(3)
    public void testCreatePlanetWithLeadingAndTrailingSpaces() {
        // Arrange
        int ownerId = 1;
        planet.setName("  Earth  ");
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth")).thenReturn(null);
        Mockito.when(planetDao.createPlanet(planet)).thenReturn(planet);

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNotNull(createdPlanet);
        Assertions.assertEquals("earth", createdPlanet.getName());
    }

    @Test
    @DisplayName("Add Planet::Valid - Name with Numbers")
    @Order(4)
    public void testCreatePlanetWithNumbers() {
        // Arrange
        int ownerId = 1;
        planet.setName("Earth123");
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth123")).thenReturn(null);
        Mockito.when(planetDao.createPlanet(planet)).thenReturn(planet);

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNotNull(createdPlanet);
        Assertions.assertEquals("earth123", createdPlanet.getName());
    }

    @Test
    @DisplayName("Add Planet::Valid - Name with Special Characters")
    @Order(5)
    public void testCreatePlanetWithSpecialCharacters() {
        // Arrange
        int ownerId = 1;
        planet.setName("Earth!@#");
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth!@#")).thenReturn(null);
        Mockito.when(planetDao.createPlanet(planet)).thenReturn(planet);

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNotNull(createdPlanet);
        Assertions.assertEquals("earth!@#", createdPlanet.getName());
    }

    @Test
    @DisplayName("Add Planet::Invalid - Planet Already Exists")
    @Order(6)
    public void testCreatePlanetAlreadyExists() {
        // Arrange
        int ownerId = 1;
        planet.setName("Earth");
        Planet existingPlanet = new Planet();
        existingPlanet.setName("earth");
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth")).thenReturn(existingPlanet);

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNull(createdPlanet);
    }

    @Test
    @DisplayName("Add Planet::Invalid - Empty Name")
    @Order(7)
    public void testCreatePlanetWithEmptyName() {
        // Arrange
        int ownerId = 1;
        planet.setName("");

        // Act & Assert
        Assertions.assertThrows(PlanetFailException.class, () -> planetService.createPlanet(ownerId, planet));
    }

    @Test
    @DisplayName("Add Planet::Invalid - Name Too Long")
    @Order(8)
    public void testCreatePlanetWithNameTooLong() {
        // Arrange
        int ownerId = 1;
        planet.setName("ThisPlanetNameIsWayTooLongAndExceedsTheMaximumAllowedLength");

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNull(createdPlanet);
    }

    @Test
    @DisplayName("Add Planet::Invalid - Non-ASCII Name")
    @Order(9)
    public void testCreatePlanetWithNonAsciiName() {
        // Arrange
        int ownerId = 1;
        planet.setName("Землѧ");

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNull(createdPlanet);
    }

    @Test
    @DisplayName("Add Planet::Invalid - Name Containing SQL Injection Characters")
    @Order(10)
    public void testCreatePlanetWithSQLInjectionName() {
        // Arrange
        int ownerId = 1;
        planet.setName("Earth; DROP TABLE planets;");

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNull(createdPlanet);
    }

    @Test
    @DisplayName("Add Planet::Invalid - Unauthorized")
    @Order(11)
    public void testCreatePlanetUnauthorized() {
        // Arrange
        int ownerId = 0;
        planet.setName("Earth");

        // Act
        Planet createdPlanet = planetService.createPlanet(ownerId, planet);

        // Assert
        Assertions.assertNull(createdPlanet);
    }
}