package tests;
import base.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
public class LoginTest extends BaseTest {
    @Test public void validLogin() throws Exception {
        login(); screenshot("valid-login");
    }
    @Test public void invalidPassword() {
        LoginPage page = new LoginPage(driver); page.open();
        page.login(Config.credential("email"), "Incorrect-Password-For-Test-Only-837!");
        Assert.assertFalse(page.errorMessage().isBlank(), "A login error must be visible.");
    }
}
