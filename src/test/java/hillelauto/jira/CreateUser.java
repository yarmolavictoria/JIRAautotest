package hillelauto.jira;
import hillelauto.Tools;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByName;

import java.sql.Timestamp;


public class CreateUser {
    private WebDriver browser;
    public CreateUser (WebDriver browser) {
        this.browser = browser;
    }
    public void createUsers () throws InterruptedException {
        browser.get(JiraVars.userSettingsURL);
        WebElement adminPass = browser.findElement(By.name("webSudoPassword"));
        adminPass.sendKeys(JiraVars.password);
        WebElement confirmPass  = browser.findElement(By.id("login-form-submit"));
        confirmPass.click();
        WebElement createUser  = browser.findElement(By.id("create_user"));
        createUser.click();
        WebElement fillEmail = browser.findElement(By.id("user-create-email"));
        fillEmail.sendKeys(JiraVars.userEmail);
        WebElement fillFullName = browser.findElement(By.id("user-create-fullname"));
        fillFullName.sendKeys(JiraVars.fullName);
        WebElement userName = browser.findElement(By.id("user-create-username"));
        userName.sendKeys(JiraVars.newUsername);
        WebElement userPass = browser.findElement(By.id("password"));
        userPass.sendKeys(JiraVars.newPassword);
        Thread.sleep(1000);
        browser.findElement(By.id("user-create-submit")).click();
  }
}
/// Add Assert

