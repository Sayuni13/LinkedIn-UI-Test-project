package tests;
import base.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
public class SearchTest extends BaseTest {
    @Test public void searchShowsResults() throws Exception {
        login(); new HomePage(driver).search(Config.get("search.term", "Microsoft"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/search/results/"));
        Assert.assertTrue(new SearchResultsPage(driver).hasResult());
        screenshot("search-results");
    }
}
