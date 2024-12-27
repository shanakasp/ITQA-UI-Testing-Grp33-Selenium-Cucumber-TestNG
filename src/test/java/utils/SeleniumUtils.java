package utils;

import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SeleniumUtils {
    private static WebDriver staticDriver;
    private static WebDriver driver;
    private WebDriverWait wait;
    private Scenario scenario;
    private static int scenarioCount = 0;

    public SeleniumUtils(Scenario scenario) {
        this.scenario = scenario;
        synchronized (SeleniumUtils.class) {
            scenarioCount++;

            if (staticDriver == null) {
                initializeDriver();
                staticDriver = driver;
            } else {
                driver = staticDriver;
            }

            wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        }
    }

    private void initializeDriver() {
        System.setProperty("webdriver.chrome.driver",
                "src/test/resources/driver/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");

        options.addArguments(
                "--remote-allow-origins=*",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-extensions"
        );

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public WebElement findElementWithWait(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement findClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void addDelay(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Delay interrupted: " + e.getMessage());
        }
    }

    public void captureScreenshot(String stepName) {
        try {
            File screenshotDir = new File("target/screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "screenshot_" + stepName + "_" + timestamp + ".png";
            File screenshot = new File(screenshotDir, fileName);

            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshotFile.toPath(), screenshot.toPath());

            System.out.println("Screenshot saved: " + screenshot.getAbsolutePath());

            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotBytes, "image/png", stepName);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }

    public boolean isPasswordValid(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).+$";
        return password.matches(passwordPattern);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void quitDriver() {
        synchronized (SeleniumUtils.class) {
            scenarioCount--;
            if (scenarioCount == 0 && staticDriver != null) {
                staticDriver.quit();
                staticDriver = null;
            }
        }
    }

    public boolean waitForCondition(java.util.function.Function<WebDriver, Boolean> condition, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return customWait.until(driver -> condition.apply(driver));
        } catch (TimeoutException e) {
            System.err.println("Condition not met within " + timeoutSeconds + " seconds.");
            return false;
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }
}
