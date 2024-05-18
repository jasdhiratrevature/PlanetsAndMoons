package com.revature.selenium.runners.authentication;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/authentication/register.feature",
        glue = "com.revature.selenium.steps.authentication.register"
)
public class RegisterRunner {
}
