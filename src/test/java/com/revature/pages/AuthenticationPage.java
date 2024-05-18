package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthenticationPage {
    private WebDriver driver;

    @FindBy(id = "usernameInput")
    private WebElement usernameField;

    @FindBy(id = "passwordInput")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"loginForm\"]/input[@value=\"Create\"]")
    private WebElement registerButton;

    @FindBy(xpath = "//*[@id=\"loginForm\"]/input[@value=\"Login\"]")
    private WebElement loginButton;

    @FindBy(id = "greeting")
    private WebElement greetingBtn;

    public AuthenticationPage(WebDriver driver) {
        this.driver = driver;
        System.out.println("In Authentication page: " + driver.getCurrentUrl());
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickRegister() {
        registerButton.click();
    }

    public void clickLogin() {
        loginButton.click();
    }

    public void waitForLoginPageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(loginButton));
    }

    public void waitForHomePageLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(greetingBtn));
    }
}