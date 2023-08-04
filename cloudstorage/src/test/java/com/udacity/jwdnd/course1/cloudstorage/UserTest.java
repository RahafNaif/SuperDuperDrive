package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    @LocalServerPort
    private int port;
    private WebDriver driver;
    private String firstName = "rahaf";
    private String lastName = "naif";
    private String userName = "rnsa";
    private String password = "1234";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testUnauthorizedUserAccess() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testSignUp() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.sendKeys(firstName);
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.sendKeys(lastName);
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.sendKeys(userName);
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.sendKeys(password);

        WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));
        signUpButton.click();
    }

    @Test
    public void testLoginLogout() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.sendKeys(userName);
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.sendKeys(password);
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        Assertions.assertEquals("Home", driver.getTitle());

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement logoutButton = driver.findElement(By.id("logoutButton"));
        logoutButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Assertions.assertEquals("Login", driver.getTitle());
    }
}
