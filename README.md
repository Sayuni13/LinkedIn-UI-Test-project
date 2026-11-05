# LinkedIn UI Automation Tests

This project tests four LinkedIn functions using Java, Selenium WebDriver and TestNG:

- Login with valid credentials
- Login with an incorrect password
- Search for a term
- Open a search result and check its destination

## Requirements

- Java 17 or later
- Eclipse IDE for Java Developers with Maven support
- Google Chrome
- Internet connection

The project was tested with Java 21, Selenium 4.35.0 and TestNG 7.11.0. Maven downloads the project dependencies. Selenium Manager handles ChromeDriver automatically.

## Open the project in Eclipse

1. Extract the project ZIP.
2. Open Eclipse and select **File > Import > Maven > Existing Maven Projects**.
3. Select the project folder containing `pom.xml`.
4. Select the detected project and click **Finish**.
5. Wait for Maven to download the dependencies.
6. If dependencies are not loaded, right-click the project and select **Maven > Update Project**.
7. Under **Window > Preferences > Java > Installed JREs**, select a JDK version 17 or later.

## Configure credentials

The private `config.properties` file is not included in the submission.

1. Copy `config.example.properties` in the project root.
2. Rename the copy to `config.properties`.
3. Replace the placeholders with your own LinkedIn email and password.
4. Save the file beside `pom.xml`.

```properties
email=YOUR_EMAIL
password=YOUR_PASSWORD
search.term=Microsoft
wait.seconds=30
manual.verification=true
verification.seconds=180
```

Use only your own account. Do not upload or share `config.properties`. It is excluded by `.gitignore`.

If `LINKEDIN_EMAIL` and `LINKEDIN_PASSWORD` environment variables are set, they override the credentials in the file. In a properties file, a literal backslash must be written as `\\`.

## Run the tests

In Eclipse, right-click the project and select **Run As > Maven build...**. Check that **Base directory** points to the project folder.

Enter `test` in **Goals** and click **Run** to run all four assignment tests. Maven runs TestNG, so a separate Eclipse TestNG plugin is not required for this method.

To run one test, use the relevant Goals value below:

| Test | Eclipse Maven Goals |
| --- | --- |
| Login page check | `-Dtest=LoginPageTest test` |
| Valid login | `-Dtest=LoginTest#validLogin test` |
| Incorrect password | `-Dtest=LoginTest#invalidPassword test` |
| Search | `-Dtest=SearchTest test` |
| Open a result | `-Dtest=SearchResultClickTest test` |

The login page check is a separate diagnostic test. It is not included in the default four-test suite and does not need credentials.

A new Chrome window opens for each test and closes when that test finishes. The incorrect-password test supplies its own wrong password; do not change the real password in your configuration to run it.

You can also run the suite from PowerShell in the project folder:

```powershell
mvn test
```

For an individual test, place `mvn` before the Goals value shown in the table.

## Security verification

LinkedIn may display a CAPTCHA, email code request or another verification step. Complete it manually in the Chrome window opened by the test. Do not bypass it.

With `manual.verification=true`, the valid-login check waits up to `verification.seconds` for the feed page. This extra wait applies after the credentials are submitted. It does not extend the incorrect-password test or the initial email-field check.

If verification cannot be completed, record the run as blocked by verification. Avoid repeatedly running the incorrect-password test.

## Results and screenshots

Check the Eclipse Console for the test count, failures, errors and skipped tests. A successful full run shows four tests with zero failures, errors and skips, followed by `BUILD SUCCESS`.

- Detailed test reports: `target/surefire-reports`
- Automatic browser screenshots: `screenshots`
- Failed test screenshots: `screenshots/<test-name>-failure.png`

Successful flow screenshots include `valid-login.png`, `search-results.png` and `opened-result.png`. Later runs replace images with the same filenames, so copy any evidence you want to keep before another run.

On failure, the Console also prints the page URL and title. Press **F5** on the project to refresh the screenshot folder in Eclipse.

Cover personal information before sharing screenshots or the recording. Never show passwords or verification codes.

## Project structure

| Location | Purpose |
| --- | --- |
| `src/test/java/base` | Configuration, browser setup and screenshot capture |
| `src/test/java/pages` | Page locators, waits and browser actions |
| `src/test/java/tests` | Test cases and assertions |
| `pom.xml` | Dependencies and Maven configuration |
| `testng.xml` | The four-test suite |
| `config.example.properties` | Example settings without real credentials |

The project uses Page Object Model. `BaseTest` opens and closes Chrome. The shared `Page` class provides explicit waits and retries stale element references during polling.

## Locators used

- Email: `input[type='email']`
- Password: `input[type='password']`
- Sign in: `//button[normalize-space(.)='Sign in']`
- Login error: `//*[normalize-space(.)='Wrong email or password.']`
- Search: input placeholder or aria-label containing `Search`, with a class fallback
- Results: visible `/in/` or `/company/` links inside `main`

The result-click test captures a result's link before clicking and checks the destination's LinkedIn host and path. Search results can change, so a search for Microsoft may open a related person rather than the company page.

LinkedIn can change its layout and element attributes. If a test times out, inspect the saved screenshot and page details before updating the relevant locator. Some locators depend on the English interface.

## Submission files

Include the source code and README, the 2–4 page PDF report, and a screen recording of no more than three minutes. Share the Git repository with the instructor.

Keep `config.properties` out of the submission. The recipient can create it from the example file and use their own account. The `target` folder is generated by Maven and does not need to be included in the source ZIP.
