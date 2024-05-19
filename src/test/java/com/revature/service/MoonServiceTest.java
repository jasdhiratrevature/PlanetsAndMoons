package com.revature.service;

import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.repository.MoonDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        moon.setName("Moon");
        moon.setMyPlanetId(1);
        planet = new Planet();
        planet.setId(1);
        planet.setName("Earth");
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
}