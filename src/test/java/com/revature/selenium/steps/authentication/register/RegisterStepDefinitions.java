package com.revature.selenium.steps.authentication.register;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.pages.AuthenticationPage;
import com.revature.repository.UserDao;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.revature.selenium.utils.DriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterStepDefinitions {
    private WebDriver driver;
    private AuthenticationPage authenticationPage;
    private UserDao userDao;

    @Before
    public void setUp() {
        driver = getDriver();
        authenticationPage = new AuthenticationPage(driver);
    }

    @After
    public void tearDown() {
        quitDriver(driver);
    }

    @BeforeAll
    public static void beforeSetup() {
        cleanUsersTable();
    }

    @Given("the user is on the Register page")
    public void theUserIsOnTheRegisterPage() {
        driver.get("http://localhost:7000/webpage/create");
        assertTrue(driver.getCurrentUrl().contains("http://localhost:7000/webpage/create"), "User is not on the Register page");
    }

    @When("the user enters {string} as the username and {string} as the password")
    public void enterCredentials(String username, String password) {
        System.out.println(username);
        authenticationPage.enterUsername(username);
        authenticationPage.enterPassword(password);
    }

    @And("the user clicks on the Register button")
    public void clickSubmit() {
        authenticationPage.clickRegister();
    }

    @Then("a successful alert should be displayed")
    public void successfulAlertDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String alertText = wait.until(ExpectedConditions.alertIsPresent()).getText();
        assertTrue(alertText.contains("Account created successfully"), "Successful alert not displayed");
        driver.switchTo().alert().accept();
    }

    @Then("an error message should be displayed")
    public void errorAlertDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String alertText = wait.until(ExpectedConditions.alertIsPresent()).getText();
        assertTrue(alertText.contains("Account creation failed"), "Error alert not displayed");
        driver.switchTo().alert().accept();
    }

    @And("the user is directed to Login page")
    public void directedToLoginPage() {
        authenticationPage.waitForLoginPageLoad();
        String pageTitle = driver.getTitle();
        assertEquals("Planetarium Login", pageTitle, "User is not directed to the Login page");
    }


    @Given("the username {string} and {string} already exists")
    public void theUsernameAndAlreadyExists(String username, String password) {
        userDao = new UserDao();
        User existingUser = userDao.getUserByUsername(username);
        assertNotNull(existingUser);
    }
}