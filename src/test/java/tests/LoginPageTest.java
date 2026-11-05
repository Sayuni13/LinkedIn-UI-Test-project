package tests;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
public class LoginPageTest extends BaseTest {
    @Test public void loginPageOpens() throws Exception {
        LoginPage page = new LoginPage(driver); page.open();
        Assert.assertTrue(page.emailVisible());
        screenshot("login-page");
    }
}
