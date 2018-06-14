package hillelauto;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class WebDriverTestBase {
    protected WebDriver browser;

    static {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Vika/Downloads/chromedriver_win32/chromedriver.exe");
    }

    @BeforeTest(alwaysRun = true)
    public void setUp() {
        browser = new ChromeDriver(new ChromeOptions().addArguments("--start-maximized", "--incognito"));
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        Tools.setDriver(browser);
    }

    @AfterTest(alwaysRun = true)
    public void finish() {
        browser.close();
    }
}