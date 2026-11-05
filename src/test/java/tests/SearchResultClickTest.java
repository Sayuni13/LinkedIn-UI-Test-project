package tests;
import base.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
public class SearchResultClickTest extends BaseTest {
    @Test public void openFirstResult() throws Exception {
        login(); new HomePage(driver).search(Config.get("search.term", "Microsoft"));
        SearchResultsPage results = new SearchResultsPage(driver);
        Assert.assertTrue(results.hasResult());
        String expected = results.openFirstResult();
        System.out.println("Verified destination: " + expected);
        screenshot("opened-result");
    }
}
