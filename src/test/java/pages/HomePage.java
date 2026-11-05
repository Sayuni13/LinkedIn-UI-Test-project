package pages;
import org.openqa.selenium.*;
public class HomePage extends Page {
    private final By search = By.cssSelector("input[placeholder*='Search'], input[aria-label*='Search'], input.search-global-typeahead__input");
    public HomePage(WebDriver driver) { super(driver); }
    public void search(String term) {
        wait.until(d -> {
            WebElement field = visible(search);
            field.click(); field.clear(); field.sendKeys(term); field.sendKeys(Keys.ENTER);
            return true;
        });
        wait.until(d -> d.getCurrentUrl().contains("/search/results/"));
    }
}
