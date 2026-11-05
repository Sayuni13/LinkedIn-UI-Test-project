package pages;
import java.net.URI;
import org.openqa.selenium.*;
public class SearchResultsPage extends Page {
    // Restrict to actual profile/company links inside the results area.
    private final By resultLinks = By.cssSelector("main a[href*='/company/'], main a[href*='/in/']");
    public SearchResultsPage(WebDriver driver) { super(driver); }
    public boolean hasResult() { return visible(resultLinks).isDisplayed(); }
    public String openFirstResult() {
        String destination = wait.until(d -> {
            for (WebElement link : d.findElements(resultLinks)) {
                if (link.isDisplayed() && link.isEnabled()) {
                    String href = link.getAttribute("href");
                    link.click(); return href;
                }
            }
            return null;
        });
        String path = URI.create(destination).getPath().replaceAll("/+$", "");
        wait.until(d -> {
            for (String handle : d.getWindowHandles()) {
                d.switchTo().window(handle);
                URI current = URI.create(d.getCurrentUrl());
                String host = current.getHost();
                String actual = current.getPath().replaceAll("/+$", "");
                if (host != null && (host.equals("linkedin.com") || host.endsWith(".linkedin.com"))
                    && (actual.equals(path) || actual.startsWith(path + "/"))) return true;
            }
            return false;
        });
        return destination;
    }
}
