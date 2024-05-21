package com.revature.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;

    @FindBy(id = "greeting")
    private WebElement greeting;

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "deleteInput")
    private WebElement deleteInput;

    @FindBy(id = "deleteButton")
    private WebElement deleteButton;

    @FindBy(id = "searchPlanetInput")
    private WebElement searchPlanetInput;

    @FindBy(id = "searchPlanetButton")
    private WebElement searchPlanetButton;

    @FindBy(id = "searchMoonInput")
    private WebElement searchMoonInput;

    @FindBy(id = "searchMoonButton")
    private WebElement searchMoonButton;


    @FindBy(id = "inputContainer")
    private WebElement inputContainer;

    @FindBy(id = "planetNameInput")
    private WebElement planetNameInput;

    @FindBy(xpath = "//*[@id='inputContainer']//button[contains(text(), 'Submit Planet')]")
    private WebElement planetSubmitButton;


    @FindBy(id = "locationSelect")
    private WebElement locationSelect;

    @FindBy(id = "celestialTable")
    private WebElement celestialTable;

    public HomePage(WebDriver driver) {
        System.out.println("In HomePage POM");
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }

    public void enterDeleteInput(String input) {
        deleteInput.sendKeys(input);
    }

    public void enterPlanetNameAddInput(String input) {
        planetNameInput.sendKeys(input);
    }

    public void clickDeleteButton() {
        deleteButton.click();
    }

    public void clickSubmitPlanetButton() {
        planetSubmitButton.click();
    }

    public void enterSearchPlanetInput(String input) {
        searchPlanetInput.sendKeys(input);
    }

    public void clickSearchPlanetButton() {
        searchPlanetButton.click();
    }

    public boolean isPlanetInTable(String planetName) {

        List<WebElement> rows = celestialTable.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                String type = cells.get(0).getText();
                String name = cells.get(2).getText();
                String owner = cells.get(3).getText();

                if ("planet".equals(type) && planetName.equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getPlanetIdByName(String planetName) {
        List<WebElement> rows = celestialTable.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty()) {
                String type = cells.get(0).getText();
                String id = cells.get(1).getText();
                String name = cells.get(2).getText();
                String owner = cells.get(3).getText();

                if ("planet".equals(type) && planetName.equals(name)) {
                    try {
                        return Integer.parseInt(id);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return -1; // Return -1 if the ID is not a valid integer
                    }
                }
            }
        }
        return -1; // Return -1 if the planet is not found
    }


    // Method to check which option is selected in locationSelect
    public String getSelectedLocationOption() {
        Select select = new Select(locationSelect);
        return select.getFirstSelectedOption().getText();
    }

    // Method to check if option 1 or option 2 is selected
    public boolean isOptionSelected(String optionText) {
        Select select = new Select(locationSelect);
        return select.getFirstSelectedOption().getText().equalsIgnoreCase(optionText);
    }

    // Method to select a desired option in locationSelect
    public void selectLocationOption(String optionText) {
        Select select = new Select(locationSelect);
        select.selectByVisibleText(optionText);
    }
}
