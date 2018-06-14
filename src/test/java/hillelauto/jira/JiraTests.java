package hillelauto.jira;

import com.sun.glass.events.KeyEvent;
import org.junit.runner.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import hillelauto.WebDriverTestBase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class JiraTests extends WebDriverTestBase {
    private LoginPage loginPage;
    private IssuePage issuePage;
    private String expectedMD5;

    @BeforeClass(alwaysRun = true)
    public void initPages() throws IOException {
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        issuePage = PageFactory.initElements(browser, IssuePage.class);
        System.out.println("Jira Pages Initialized");
        expectedMD5 = calculateHash(JiraVars.attachmentFileLocation + JiraVars.attachmentFileName);
    }

    public String calculateHash(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        expectedMD5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
        fis.close();
        return expectedMD5;
    }

    @Test(description = "1. Invalid Login", priority = -1)
    public void failureLogin() {
        loginPage.failureLogin();
    }

    @Test(description = "2. Valid Login", groups = { "Sanity" })
    public void successfulLogin() {
        loginPage.successfulLogin();
    }

    @Test(description = "3. Create issue", dependsOnMethods = { "successfulLogin" }, groups = { "Sanity", "Issues" })
    public void createIssue() {
        issuePage.createIssue();
    }

    @Test(description = "4. Open issue", dependsOnMethods = { "createIssue" }, groups = { "Sanity", "Issues" })
    public void openIssue() {
        issuePage.openIssue();
    }

    @Test(description = "5. Uplaod Attachment", dependsOnMethods = { "openIssue" }, groups = { "Issues.Attachments" })
    public void uploadAttachment() {
        issuePage.uploadAttachment();
    }

    @Test(description = "Download Attachment", dependsOnMethods = { "uploadAttachment" }, groups = {
            "Issues.Attachments", "disabled" })
    public void downloadAttachment() throws IOException {
        issuePage.downloadAttachment();
        String resultMD5 = calculateHash(JiraVars.downloadFileLocation + JiraVars.attachmentFileName);
        assertEquals(resultMD5,expectedMD5);
        //  browser.get(JiraVars.downloads);
        //Assert.assertTrue(browser.findElement(By.id("title-area")).equals(JiraVars.attachmentFileName));

    }

}
