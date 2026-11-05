package pages;

import java.net.URI;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.Config;

public class LoginPage extends Page {

    private final By email =
            By.cssSelector("input[type='email']");

    private final By password =
            By.cssSelector("input[type='password']");

    private final By submit = By.xpath(
            "//button[normalize-space(.)='Sign in']"
    );

    private final By error = By.xpath(
            "//*[normalize-space(.)='Wrong email or password.']"
    );

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.linkedin.com/login");
    }

    public boolean emailVisible() {
        return visible(email).isDisplayed();
    }

    public void login(String user, String secret) {

        WebElement emailBox = visible(email);
        emailBox.clear();
        emailBox.sendKeys(user);

        WebElement passwordBox = visible(password);
        passwordBox.clear();
        passwordBox.sendKeys(secret);

        click(submit);
    }

    public boolean loggedIn() {

        int seconds = Config.seconds();

        if (Boolean.parseBoolean(
                Config.get("manual.verification", "false"))) {

            seconds = Integer.parseInt(
                    Config.get("verification.seconds", "120")
            );
        }

        WebDriverWait loginWait = new WebDriverWait(
                driver,
                Duration.ofSeconds(seconds)
        );

        return loginWait.until(browser -> {

            URI current = URI.create(browser.getCurrentUrl());

            String host = current.getHost();
            String path = current.getPath();

            boolean linkedinHost = host != null
                    && (host.equals("linkedin.com")
                    || host.endsWith(".linkedin.com"));

            boolean feedPage = path != null
                    && (path.equals("/feed")
                    || path.startsWith("/feed/"));

            return linkedinHost && feedPage;
        });
    }

    public String errorMessage() {
        return visible(error).getText();
    }
}