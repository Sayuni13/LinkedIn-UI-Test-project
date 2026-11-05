package base;
import java.nio.file.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
public class BaseTest {
    protected WebDriver driver;
    @BeforeMethod public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    protected void login() {
        LoginPage page = new LoginPage(driver);
        page.open(); page.login(Config.credential("email"), Config.credential("password"));
        page.loggedIn();
    }
    protected void screenshot(String name) throws Exception {
        Files.createDirectories(Path.of("screenshots"));
        Files.write(Path.of("screenshots", name + ".png"),
            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    }
    @AfterMethod(alwaysRun = true) public void tearDown(ITestResult result) {
        if (driver == null) return;
        try {
            if (!result.isSuccess()) {
                System.out.println("PAGE URL: " + driver.getCurrentUrl());
                System.out.println("PAGE TITLE: " + driver.getTitle());
                screenshot(result.getName() + "-failure");
            }
        } catch (Exception e) { System.out.println("Screenshot could not be saved: " + e.getMessage()); }
        finally { driver.quit(); }
    }
}
