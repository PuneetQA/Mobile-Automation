package Tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;

public class Logintest {

    private AndroidDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        // Desired capabilities for Appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.0"); // Android version
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554"); // Emulator ID
        capabilities.setCapability(MobileCapabilityType.APP, "/path/to/your/app.apk"); // Path to APK file
        capabilities.setCapability(MobileCapabilityType.NO_RESET, true); // Keep app state intact
        
        // Initialize the AndroidDriver
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @Test(priority = 1)
    public void testLogin() throws InterruptedException {
        // Locate and interact with login elements
        WebElement usernameField = driver.findElement(By.id("com.goequipme:id/username"));
        WebElement passwordField = driver.findElement(By.id("com.goequipme:id/password"));
        WebElement loginButton = driver.findElement(By.id("com.goequipme:id/login_button"));

        // Test login with valid credentials
        usernameField.sendKeys("testuser");
        passwordField.sendKeys("password123");
        loginButton.click();

        // Wait for login process to complete
        Thread.sleep(5000); // ideally, use WebDriverWait here
        
        // Validate that the user is logged in (e.g., by checking for a logout button)
        WebElement logoutButton = driver.findElement(By.id("com.example:id/logout_button"));
        Assert.assertNotNull(logoutButton, "Login failed!");
    }

    @Test(priority = 2)
    public void testSignup() throws InterruptedException {
        // Locate and interact with signup elements
        WebElement usernameField = driver.findElement(By.id("com.goequipme:id/username"));
        WebElement emailField = driver.findElement(By.id("com.goequipme:id/email"));
        WebElement passwordField = driver.findElement(By.id("com.goequipme:id/password"));
        WebElement signupButton = driver.findElement(By.id("com.goequipme:id/signup_button"));

        // Test signup with new user
        usernameField.sendKeys("newuser");
        emailField.sendKeys("newuser@example.com");
        passwordField.sendKeys("password123");
        signupButton.click();

        // Wait for signup process to complete
        Thread.sleep(5000); // ideally, use WebDriverWait here
        
        // Validate signup success (check for a specific element that appears after signup)
        WebElement welcomeMessage = driver.findElement(By.id("com.goequipme:id/welcome_message"));
        Assert.assertNotNull(welcomeMessage, "Signup failed!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
