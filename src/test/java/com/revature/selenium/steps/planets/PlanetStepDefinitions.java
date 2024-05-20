package com.revature.selenium.steps.planets;

import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.pages.AuthenticationPage;
import com.revature.pages.HomePage;
import com.revature.repository.PlanetDao;
import com.revature.repository.UserDao;
import com.revature.service.PlanetService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.revature.selenium.utils.DriverUtils.*;

public class PlanetStepDefinitions {
    private WebDriver driver;
    private HomePage homePage;
    private AuthenticationPage authenticationPage;
    private UserDao userDao;
    private PlanetDao planetDao;
    private PlanetService planetService;
    private User testUser;

    @Before
    public void setUp() {
        driver = getDriver();
        homePage = new HomePage(driver);
        authenticationPage = new AuthenticationPage(driver);
    }

    @After
    public void tearDown() {
        quitDriver(driver);
    }

    @BeforeAll
    public static void beforeSetup() {
        cleanDatabaseTable();
    }


    @Given("the user has an existing account")
    public void theUserHasAnExistingAccount() {
        userDao = new UserDao();
        testUser = new User();

        String username = "testuser1";
        String password = "validassword";
        UsernamePasswordAuthentication user = new UsernamePasswordAuthentication();
        user.setUsername(username);
        user.setPassword(password);

        if (userDao.getUserByUsername(username) == null) {
            testUser = userDao.createUser(user);
            assertNotNull(user);
            assertEquals(username, testUser.getUsername());
        }
    }

    @And("the user is logged in")
    public void theUserIsLoggedIn() {
        driver.get("http://localhost:7000/webpage/login");
        authenticationPage.enterUsername("testuser1");
        authenticationPage.enterPassword("validassword");
        authenticationPage.clickLogin();
        authenticationPage.waitForHomePageLoad();
    }

    @And("the user is on the home page")
    public void theUserIsOnTheHomePage() {
        String pageTitle = driver.getTitle();
        assertEquals("Home", pageTitle, "User is not directed to the Home page");

        // Check if ownerID is present in sessionStorage
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String ownerId = (String) js.executeScript("return sessionStorage.getItem('userId');");
        String userName = (String) js.executeScript("return sessionStorage.getItem('user');");
        assertTrue(ownerId != null && !ownerId.isEmpty(), "ownerID is not present in sessionStorage");
        assertEquals("testuser1", userName);

        testUser.setId(Integer.parseInt(ownerId));
        testUser.setUsername(userName);
    }

    @Given("the planet name {string} does not already exist")
    public void thePlanetDoesNotAlreadyExist(String planetName) {
        planetDao = new PlanetDao();
        planetService = new PlanetService(planetDao);
        assertNull(planetService.getPlanetByName(testUser.getId(), planetName));
    }

    @And("the Planet option is selected in the location select")
    public void thePlanetOptionIsSelectedInTheLocationSelect() {
        if (!homePage.isOptionSelected("Planet")) {
            homePage.selectLocationOption("Planet");
        }
        String selectedOption = homePage.getSelectedLocationOption();
        assertEquals(selectedOption, "Planet");
    }

    @When("the user enters {string} in the planet input")
    public void theUserEntersInThePlanetInput(String planetName) {
        homePage.enterPlanetNameInput(planetName);
    }

    @And("clicks the submit planet button")
    public void clicksTheSubmitPlanetButton() {
        homePage.clickSubmitPlanetButton();
    }

    @Then("the planet name {string} should be added successfully to the Celestial Table")
    public void thePlanetShouldBeAddedSuccessfullyToTheCelestialTable(String planetName) throws InterruptedException {
        // Set implicit wait of 3 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        Thread.sleep(1500);

        planetDao = new PlanetDao();
        planetService = new PlanetService(planetDao);
        Planet planet = planetService.getPlanetByName(testUser.getId(), planetName);

        // Assert that the method returns a planet
        assertNotNull(planet, "Planet should be found by the service");

        // Query the table to check for the matching row
        boolean isPlanetInTable = homePage.isPlanetInTable(planet.getName(), planet.getOwnerId());

        // Assert that the planet is found in the table
        assertTrue(isPlanetInTable, "Planet should be found in the celestial table");
    }

    @Then("the alert should be displayed for Planet Adding Error")
    public void theAlertShouldBeDisplayedForPlanetAddingError() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String alertText = wait.until(ExpectedConditions.alertIsPresent()).getText();
        assertTrue(alertText.contains("error"), "Error alert not displayed");
        driver.switchTo().alert().accept();
    }

    @Given("the planet name {string} already exists")
    public void thePlanetNameAlreadyExists(String planetName) {
        assertTrue(homePage.isPlanetInTable(planetName, testUser.getId()));
    }
}
