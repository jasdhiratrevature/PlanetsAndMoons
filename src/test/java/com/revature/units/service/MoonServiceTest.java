package com.revature.units.service;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.repository.MoonDao;
import com.revature.service.MoonService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoonServiceTest {

    @Mock
    private MoonDao moonDao;

    @InjectMocks
    private MoonService moonService;

    private Moon moon;
    private Planet planet;

    @BeforeEach
    public void setUp() {
        moon = new Moon();
        moon.setName("moon");
        moon.setMyPlanetId(1);
        planet = new Planet();
        planet.setId(1);
        planet.setName("earth");
        planet.setOwnerId(1);
    }

    @Test
    @DisplayName("Add Moon::Valid - Single")
    @Order(1)
    public void testCreateMoonSingle() {
        // Arrange
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(null);
        Mockito.when(moonDao.createMoon(moon)).thenReturn(moon);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNotNull(createdMoon);
        Assertions.assertEquals("moon", createdMoon.getName());
        Assertions.assertEquals(1, createdMoon.getMyPlanetId());
    }

    @Test
    @DisplayName("Add Moon::Valid - Multiple")
    @Order(2)
    public void testCreateMoonMultiple() {
        // Arrange
        Moon moon2 = new Moon();
        moon2.setName("Phobos");
        moon2.setMyPlanetId(1);


        //Act
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(null);
        Mockito.when(moonDao.createMoon(moon)).thenReturn(moon);
        Moon createdMoon1 = moonService.createMoon(moon);

        Mockito.when(moonDao.createMoon(moon2)).thenReturn(moon2);
        Mockito.when(moonDao.getMoonByName("phobos")).thenReturn(null);
        Moon createdMoon2 = moonService.createMoon(moon2);

        // Assert
        Assertions.assertNotNull(createdMoon1);
        Assertions.assertNotNull(createdMoon2);
        Assertions.assertEquals(moon.getName(), createdMoon1.getName());
        Assertions.assertEquals(moon2.getName(), createdMoon2.getName());
    }

    @Test
    @DisplayName("Add Moon::Valid - Name with One Uppercase")
    @Order(3)
    public void testCreateMoonWithOneUppercase() {
        // Arrange
        moon.setName("mOon");
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(null);
        Mockito.when(moonDao.createMoon(moon)).thenReturn(moon);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNotNull(createdMoon);
        Assertions.assertEquals("moon", createdMoon.getName());
        Assertions.assertEquals(1, createdMoon.getMyPlanetId());
    }

    @Test
    @DisplayName("Add Moon::Valid - Name with All Uppercase")
    @Order(4)
    public void testCreateMoonWithAllUppercase() {
        // Arrange
        moon.setName("MOON");
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(null);
        Mockito.when(moonDao.createMoon(moon)).thenReturn(moon);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNotNull(createdMoon);
        Assertions.assertEquals("moon", createdMoon.getName());
        Assertions.assertEquals(1, createdMoon.getMyPlanetId());
    }

    @Test
    @DisplayName("Add Moon::Valid - Name with Leading and Trailing Spaces")
    @Order(5)
    public void testCreateMoonWithLeadingAndTrailingSpaces() {
        // Arrange
        moon.setName("  Moon  ");
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(null);
        Mockito.when(moonDao.createMoon(moon)).thenReturn(moon);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNotNull(createdMoon);
        Assertions.assertEquals("moon", createdMoon.getName());
        Assertions.assertEquals(1, createdMoon.getMyPlanetId());
    }

    @Test
    @DisplayName("Add Moon::Valid - Name with Numbers")
    @Order(6)
    public void testCreateMoonWithNumbers() {
        // Arrange
        moon.setName("Moon123");
        Mockito.when(moonDao.getMoonByName("moon123")).thenReturn(null);
        Mockito.when(moonDao.createMoon(moon)).thenReturn(moon);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNotNull(createdMoon);
        Assertions.assertEquals("moon123", createdMoon.getName());
        Assertions.assertEquals(1, createdMoon.getMyPlanetId());
    }

    @Test
    @DisplayName("Add Moon::Valid - Name with Special Characters")
    @Order(7)
    public void testCreateMoonWithSpecialCharacters() {
        // Arrange
        moon.setName("Moon!@#");
        Mockito.when(moonDao.getMoonByName("moon!@#")).thenReturn(null);
        Mockito.when(moonDao.createMoon(moon)).thenReturn(moon);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNotNull(createdMoon);
        Assertions.assertEquals("moon!@#", createdMoon.getName());
        Assertions.assertEquals(1, createdMoon.getMyPlanetId());
    }

    @Test
    @DisplayName("Add Moon::Invalid - Moon Already Exists")
    @Order(8)
    public void testCreateMoonAlreadyExists() {
        // Arrange
        Moon existingMoon = new Moon();
        existingMoon.setName("moon");
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(existingMoon);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Add Moon::Invalid - Empty Moon name")
    @Order(9)
    public void testCreateMoonWithEmptyName() {
        // Arrange
        moon.setName("");

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Add Moon::Invalid - Moon Name Too Long")
    @Order(10)
    public void testCreateMoonWithNameTooLong() {
        // Arrange
        moon.setName("ThisMoonNameIsWayTooLongAndExceedsTheMaximumAllowedLength");

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Add Moon::Invalid - Non-ASCII Moon Name")
    @Order(11)
    public void testCreateMoonWithNonAsciiName() {
        // Arrange
        moon.setName("Луна");

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Add Moon::Invalid - Moon Name Containing SQL Injection Characters")
    @Order(12)
    public void testCreateMoonWithSQLInjectionName() {
        // Arrange
        moon.setName("Moon; DROP TABLE moons;");

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Add Moon::Invalid - Non existing Planet ID")
    @Order(13)
    public void testCreateMoonWithNonExistingPlanetId() {
        // Arrange
        moon.setMyPlanetId(999);
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(null);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Add Moon::Invalid - Missing Planet ID")
    @Order(14)
    public void testCreateMoonWithMissingPlanetId() {
        // Arrange
        moon.setMyPlanetId(0);

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Add Moon::Invalid - Planet Name Instead of ID")
    @Order(15)
    public void testCreateMoonWithPlanetName() {
        // Arrange
        moon.setMyPlanetId(-1);
        moon.setName("Earth");

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Add Moon::Invalid - Unauthorized")
    @Order(16)
    public void testCreateMoonUnauthorized() {
        // Arrange
        moon.setMyPlanetId(1);
        planet.setOwnerId(0); // Unauthorized owner ID

        // Act
        Moon createdMoon = moonService.createMoon(moon);

        // Assert
        Assertions.assertNull(createdMoon);
    }

    @Test
    @DisplayName("Remove Moon::Valid")
    @Order(17)
    public void testDeleteMoonValid() {
        // Arrange
        int moonId = 1;

        Mockito.when(moonDao.deleteMoonById(moonId)).thenReturn(true);

        // Act
        boolean deletionResult = moonService.deleteMoonById(moonId);

        // Assert
        Assertions.assertTrue(deletionResult);
    }

    @Test
    @DisplayName("Remove Moon::Invalid - Moon Name Instead of ID")
    @Order(18)
    public void testDeleteMoonWithMoonName() {
        // Act
        boolean deletionResult = moonService.deleteMoonById(-1);

        // Assert
        Assertions.assertFalse(deletionResult);
    }

    @Test
    @DisplayName("Remove Moon::Invalid - Empty ID")
    @Order(19)
    public void testDeleteMoonWithEmptyId() {
        // Arrange
        int moonId = 0;

        // Act
        boolean deletionResult = moonService.deleteMoonById(moonId);

        // Assert
        Assertions.assertFalse(deletionResult);
    }

    @Test
    @DisplayName("Remove Moon::Invalid - Invalid ID")
    @Order(20)
    public void testDeleteMoonWithInvalidId() {
        // Arrange
        int invalidMoonId = 999;

        // Act
        boolean deletionResult = moonService.deleteMoonById(invalidMoonId);

        // Assert
        Assertions.assertFalse(deletionResult);
    }

    @Test
    @DisplayName("Remove Moon::Invalid - Unauthorized")
    @Order(21)
    public void testDeleteMoonUnauthorized() {
        // Arrange
        int unauthorizedPlanetId = 0;
        moon.setMyPlanetId(unauthorizedPlanetId);

        // Act
        boolean deletionResult = moonService.deleteMoonById(1);

        // Assert
        Assertions.assertFalse(deletionResult);
    }

    @Test
    @DisplayName("Search Moon::Valid - Name")
    @Order(22)
    public void testGetMoonByName() {
        // Arrange
        int planetId = 1;
        String testMoonName = "Moon";
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(moon);

        // Act
        Moon foundMoon = moonService.getMoonByName(planetId, testMoonName);

        // Assert
        Assertions.assertNotNull(foundMoon);
        Assertions.assertEquals("moon", foundMoon.getName());
    }

    @Test
    @DisplayName("Search Moon::Valid - ID")
    @Order(23)
    public void testGetMoonById() {
        // Arrange
        int moonId = 1;
        Mockito.when(moonDao.getMoonById(moonId)).thenReturn(moon);

        // Act
        Moon foundMoon = moonService.getMoonById(1, moonId);

        // Assert
        Assertions.assertNotNull(foundMoon);
    }

    @Test
    @DisplayName("Search Moon::Valid - Name with Uppercase")
    @Order(24)
    public void testGetMoonByNameWithUppercase() {
        // Arrange
        int planetId = 1;
        String moonName = "MOON";
        moon.setName("moon");
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(moon);

        // Act
        Moon foundMoon = moonService.getMoonByName(planetId, moonName);

        // Assert
        Assertions.assertNotNull(foundMoon);
        Assertions.assertEquals("moon", foundMoon.getName());
    }

    @Test
    @DisplayName("Search Moon::Valid - Name with Special Characters")
    @Order(25)
    public void testGetMoonByNameWithSpecialCharacters() {
        // Arrange
        int planetId = 1;
        String moonName = "Moon!@#";
        moon.setName("moon!@#");
        Mockito.when(moonDao.getMoonByName("moon!@#")).thenReturn(moon);

        // Act
        Moon foundMoon = moonService.getMoonByName(planetId, moonName);

        // Assert
        Assertions.assertNotNull(foundMoon);
        Assertions.assertEquals("moon!@#", foundMoon.getName());
    }

    @Test
    @DisplayName("Search Moon::Valid - Name with Numbers")
    @Order(26)
    public void testGetMoonByNameWithNumbers() {
        // Arrange
        int planetId = 1;
        String moonName = "Moon123";
        moon.setName("moon123");
        Mockito.when(moonDao.getMoonByName("moon123")).thenReturn(moon);

        // Act
        Moon foundMoon = moonService.getMoonByName(planetId, moonName);

        // Assert
        Assertions.assertNotNull(foundMoon);
        Assertions.assertEquals("moon123", foundMoon.getName());
    }

    @Test
    @DisplayName("Search Moon::Valid - Name with Leading and Trailing Spaces")
    @Order(27)
    public void testGetMoonByNameWithLeadingAndTrailingSpaces() {
        // Arrange
        int planetId = 1;
        String moonName = "  Moon  ";
        Mockito.when(moonDao.getMoonByName("moon")).thenReturn(moon);

        // Act
        Moon foundMoon = moonService.getMoonByName(planetId, moonName);

        // Assert
        Assertions.assertNotNull(foundMoon);
        Assertions.assertEquals("moon", foundMoon.getName());
    }

    @Test
    @DisplayName("Search Moon::Invalid - Non-Existent Name")
    @Order(28)
    public void testGetMoonByNonExistentName() {
        // Arrange
        int planetId = 1;
        String nonExistentMoonName = "nonexistentmoon";
        Mockito.when(moonDao.getMoonByName(nonExistentMoonName)).thenReturn(null);

        // Act
        Moon foundMoon = moonService.getMoonByName(planetId, nonExistentMoonName);

        // Assert
        Assertions.assertNull(foundMoon);
    }

    @Test
    @DisplayName("Search Moon::Invalid - Empty Name")
    @Order(29)
    public void testGetMoonByEmptyName() {
        // Arrange
        int planetId = 1;
        String emptyMoonName = "";

        // Act
        Moon foundMoon = moonService.getMoonByName(planetId, emptyMoonName);

        // Assert
        Assertions.assertNull(foundMoon);
    }

    @Test
    @DisplayName("Search Moon::Invalid - Name Containing SQL Injection Characters")
    @Order(30)
    public void testGetMoonByNameWithSQLInjection() {
        // Arrange
        int planetId = 1;
        String moonName = "Moon; DROP TABLE moons;";

        // Act
        Moon foundMoon = moonService.getMoonByName(planetId, moonName);

        // Assert
        Assertions.assertNull(foundMoon);
    }

    @Test
    @DisplayName("Search Moon::Invalid - Unauthorized")
    @Order(31)
    public void testGetMoonUnauthorized() {
        // Arrange
        int unauthorizedPlanetId = 0;
        String moonName = "Moon";

        // Act
        Moon foundMoon = moonService.getMoonByName(unauthorizedPlanetId, moonName);

        // Assert
        Assertions.assertNull(foundMoon);
    }

    @Test
    @DisplayName("Get All Moons::Valid")
    @Order(32)
    public void testGetAllMoonsValid() throws SQLException {
        // Arrange
        int ownerId = 1;
        List<Moon> mockMoons = new ArrayList<>();

        Moon moon1 = new Moon();
        moon1.setName("Moon");
        moon1.setMyPlanetId(1);

        Moon moon2 = new Moon();
        moon2.setName("Phobos");
        moon2.setMyPlanetId(1);

        mockMoons.add(moon1);
        mockMoons.add(moon2);

        Mockito.when(moonDao.getAllMoons(ownerId)).thenReturn(mockMoons);

        // Act
        List<Moon> moons = moonService.getAllMoons(ownerId);

        // Assert
        Assertions.assertNotNull(moons);
        Assertions.assertEquals(2, moons.size());
        Assertions.assertTrue(moons.stream().anyMatch(moon -> "Moon".equals(moon.getName())));
        Assertions.assertTrue(moons.stream().anyMatch(moon -> "Phobos".equals(moon.getName())));
    }

    @Test
    @DisplayName("Get All Moons::Invalid OwnerId")
    @Order(33)
    public void testGetAllMoonsSQLException() {
        // Arrange
        int ownerId = 100;

        Mockito.when(moonDao.getAllMoons(ownerId)).thenThrow(new MoonFailException("Error retrieving moons"));

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            moonService.getAllMoons(ownerId);
        });
    }

    @Test
    @DisplayName("Get Moons From Planet::Valid")
    @Order(34)
    public void testGetMoonsFromPlanetValid() {
        // Arrange
        int planetId = 1;
        List<Moon> mockMoons = new ArrayList<>();

        Moon moon1 = new Moon();
        moon1.setName("Moon");
        moon1.setMyPlanetId(planetId);

        Moon moon2 = new Moon();
        moon2.setName("Deimos");
        moon2.setMyPlanetId(planetId);

        mockMoons.add(moon1);
        mockMoons.add(moon2);

        Mockito.when(moonDao.getMoonsFromPlanet(planetId)).thenReturn(mockMoons);

        // Act
        List<Moon> moons = moonService.getMoonsFromPlanet(planetId);

        // Assert
        Assertions.assertNotNull(moons);
        Assertions.assertEquals(2, moons.size());
        Assertions.assertTrue(moons.stream().anyMatch(moon -> "Moon".equals(moon.getName())));
        Assertions.assertTrue(moons.stream().anyMatch(moon -> "Deimos".equals(moon.getName())));
    }

    @Test
    @DisplayName("Get Moons From Planet::Invalid - SQLException")
    @Order(35)
    public void testGetMoonsFromPlanetSQLException() {
        // Arrange
        int planetId = 100;

        Mockito.when(moonDao.getMoonsFromPlanet(planetId)).thenThrow(new RuntimeException("Error retrieving moons from planet"));

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            moonService.getMoonsFromPlanet(planetId);
        });
    }

    @Test
    @DisplayName("Create Moon::Invalid - SQLException")
    @Order(36)
    public void testCreateMoonSQLException() {
        // Arrange
        Mockito.when(moonDao.createMoon(moon)).thenThrow(new RuntimeException("Error creating moon"));

        // Act & Assert
        Assertions.assertThrows(RuntimeException.class, () -> {
            moonService.createMoon(moon);
        });
    }
}