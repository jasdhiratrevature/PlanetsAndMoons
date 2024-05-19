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
        planet.setName("earth");
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

    @Test
    @DisplayName("Remove Planet::Valid - No Moon")
    @Order(12)
    public void testRemovePlanetWithNoMoon() {
        // Arrange
        int ownerId = 1;
        int planetId = 1;
        Mockito.when(planetDao.getPlanetById(ownerId, planetId)).thenReturn(planet);
        Mockito.when(planetDao.deletePlanetById(ownerId, planetId)).thenReturn(true);

        // Act
        boolean deletionResult = planetService.deletePlanetById(ownerId, planetId);

        // Assert
        Assertions.assertTrue(deletionResult);
    }

    @Test
    @DisplayName("Remove Planet::Valid - With Moon")
    @Order(13)
    public void testRemovePlanetWithMoon() {
        // Arrange
        int ownerId = 1;
        int planetId = 1;
        Mockito.when(planetDao.getPlanetById(ownerId, planetId)).thenReturn(planet);
        Mockito.when(planetDao.deletePlanetById(ownerId, planetId)).thenReturn(true);

        // Act
        boolean deletionResult = planetService.deletePlanetById(ownerId, planetId);

        // Assert
        Assertions.assertTrue(deletionResult);
    }

    @Test
    @DisplayName("Remove Planet::Invalid - Planet Name Instead of ID")
    @Order(14)
    public void testRemovePlanetWithPlanetName() {
        // Arrange
        int ownerId = 1;
        String planetName = "Earth";

        // Act
        boolean deletionResult = planetService.deletePlanetById(ownerId, -1);

        // Assert
        Assertions.assertFalse(deletionResult);
    }

    @Test
    @DisplayName("Remove Planet::Invalid - Empty ID")
    @Order(15)
    public void testRemovePlanetWithEmptyId() {
        // Arrange
        int ownerId = 1;
        int planetId = 0;

        // Act
        boolean deletionResult = planetService.deletePlanetById(ownerId, planetId);

        // Assert
        Assertions.assertFalse(deletionResult);
    }

    @Test
    @DisplayName("Remove Planet::Invalid - Invalid ID")
    @Order(16)
    public void testRemovePlanetWithInvalidId() {
        // Arrange
        int ownerId = 1;
        int invalidPlanetId = 999;

        // Act
        boolean deletionResult = planetService.deletePlanetById(ownerId, invalidPlanetId);

        // Assert
        Assertions.assertFalse(deletionResult);
    }

    @Test
    @DisplayName("Remove Planet::Invalid - Unauthorized")
    @Order(17)
    public void testRemovePlanetUnauthorized() {
        // Arrange
        int unauthorizedOwnerId = 0;
        int planetId = 1;

        // Act
        boolean deletionResult = planetService.deletePlanetById(unauthorizedOwnerId, planetId);

        // Assert
        Assertions.assertFalse(deletionResult);
    }

    @Test
    @DisplayName("Search Planet::Valid - Name")
    @Order(18)
    public void testGetPlanetByName() {
        // Arrange
        int ownerId = 1;
        String planetName = "earth";
        Mockito.when(planetDao.getPlanetByName(ownerId, planetName)).thenReturn(planet);

        // Act
        Planet foundPlanet = planetService.getPlanetByName(ownerId, planetName);

        // Assert
        Assertions.assertNotNull(foundPlanet);
        Assertions.assertEquals(planetName, foundPlanet.getName());
    }

    @Test
    @DisplayName("Search Planet::Valid - ID")
    @Order(19)
    public void testGetPlanetById() {
        // Arrange
        int ownerId = 1;
        int planetId = 1;
        Mockito.when(planetDao.getPlanetById(ownerId, planetId)).thenReturn(planet);

        // Act
        Planet foundPlanet = planetService.getPlanetById(ownerId, planetId);

        // Assert
        Assertions.assertNotNull(foundPlanet);
    }

    @Test
    @DisplayName("Search Planet::Valid - Name with Uppercase")
    @Order(20)
    public void testGetPlanetByNameWithUppercase() {
        // Arrange
        int ownerId = 1;
        String planetName = "EARTH";
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth")).thenReturn(planet);

        // Act
        Planet foundPlanet = planetService.getPlanetByName(ownerId, planetName);

        // Assert
        Assertions.assertNotNull(foundPlanet);
        Assertions.assertEquals("earth", foundPlanet.getName());
    }

    @Test
    @DisplayName("Search Planet::Valid - Name with Special Characters")
    @Order(21)
    public void testGetPlanetByNameWithSpecialCharacters() {
        // Arrange
        int ownerId = 1;
        String testPlanetName = "Earth!@#";
        planet.setName("earth!@#");
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth!@#")).thenReturn(planet);

        // Act
        Planet foundPlanet = planetService.getPlanetByName(ownerId, testPlanetName);

        // Assert
        Assertions.assertNotNull(foundPlanet);
        Assertions.assertEquals("earth!@#", foundPlanet.getName());
    }

    @Test
    @DisplayName("Search Planet::Valid - Name with Numbers")
    @Order(22)
    public void testGetPlanetByNameWithNumbers() {
        // Arrange
        int ownerId = 1;
        String testPlanetName = "Earth123";
        planet.setName("earth123");
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth123")).thenReturn(planet);

        // Act
        Planet foundPlanet = planetService.getPlanetByName(ownerId, testPlanetName);

        // Assert
        Assertions.assertNotNull(foundPlanet);
        Assertions.assertEquals("earth123", foundPlanet.getName());
    }

    @Test
    @DisplayName("Search Planet::Valid - Name with Leading and Trailing Spaces")
    @Order(23)
    public void testGetPlanetByNameWithLeadingAndTrailingSpaces() {
        // Arrange
        int ownerId = 1;
        String planetName = "  Earth  ";
        Mockito.when(planetDao.getPlanetByName(ownerId, "earth")).thenReturn(planet);

        // Act
        Planet foundPlanet = planetService.getPlanetByName(ownerId, planetName);

        // Assert
        Assertions.assertNotNull(foundPlanet);
        Assertions.assertEquals("earth", foundPlanet.getName());
    }

    @Test
    @DisplayName("Search Planet::Invalid - Non-Existent Name")
    @Order(24)
    public void testGetPlanetByNonExistentName() {
        // Arrange
        int ownerId = 1;
        String testPlanetName = "NonExistentPlanet";
        Mockito.when(planetDao.getPlanetByName(ownerId, testPlanetName.toLowerCase())).thenReturn(null);

        // Act
        Planet foundPlanet = planetService.getPlanetByName(ownerId, testPlanetName);

        // Assert
        Assertions.assertNull(foundPlanet);
    }

    @Test
    @DisplayName("Search Planet::Invalid - Empty Name")
    @Order(25)
    public void testGetPlanetByEmptyName() {
        // Arrange
        int ownerId = 1;
        String emptyPlanetName = "";

        // Act
        Planet foundPlanet = planetService.getPlanetByName(ownerId, emptyPlanetName);

        // Assert
        Assertions.assertNull(foundPlanet);
    }

    @Test
    @DisplayName("Search Planet::Invalid - Name Containing SQL Injection Characters")
    @Order(26)
    public void testGetPlanetByNameWithSQLInjection() {
        // Arrange
        int ownerId = 1;
        String planetName = "Earth; DROP TABLE planets;";

        // Act
        Planet foundPlanet = planetService.getPlanetByName(ownerId, planetName);

        // Assert
        Assertions.assertNull(foundPlanet);
    }

    @Test
    @DisplayName("Search Planet::Invalid - Unauthorized")
    @Order(27)
    public void testGetPlanetUnauthorized() {
        // Arrange
        int unauthorizedOwnerId = 0;
        String planetName = "Earth";

        // Act
        Planet foundPlanet = planetService.getPlanetByName(unauthorizedOwnerId, planetName);

        // Assert
        Assertions.assertNull(foundPlanet);
    }
}