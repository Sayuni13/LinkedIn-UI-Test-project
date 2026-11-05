package pages;
import base.Config;
import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
public class Page {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    public Page(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Config.seconds()));
        wait.ignoring(StaleElementReferenceException.class);
    }
    protected WebElement visible(By locator) {
        return wait.until(d -> {
            for (WebElement e : d.findElements(locator)) if (e.isDisplayed()) return e;
            return null;
        });
    }
    protected void click(By locator) {
        wait.until(d -> {
            for (WebElement e : d.findElements(locator)) {
                if (e.isDisplayed() && e.isEnabled()) { e.click(); return true; }
            }
            return false;
        });
    }
}
