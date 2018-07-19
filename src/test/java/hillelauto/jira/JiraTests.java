package hillelauto.jira;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import hillelauto.WebDriverTestBase;
import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static org.junit.Assert.assertEquals;


public class JiraTests extends WebDriverTestBase {
    private LoginPage loginPage;
    private IssuePage issuePage;
    private String expectedMD5;
    private CreateUser createUser;

    @BeforeClass(alwaysRun = true)
    public void initPages() throws IOException {
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        issuePage = PageFactory.initElements(browser, IssuePage.class);
        createUser = PageFactory.initElements(browser, CreateUser.class);
        expectedMD5 = calculateHash(JiraVars.attachmentFileLocation + JiraVars.attachmentFileName);
    }

    public String calculateHash(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        expectedMD5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
        fis.close();
        return expectedMD5;
    }

    @Test(description = "1. Invalid Login", priority = -1, groups = { "Sanity" })
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

    @Test(description = "5. Uplaod Attachment", dependsOnMethods = { "openIssue" }, groups = {"Sanity", "Issues.Attachments" })
    public void uploadAttachment() {
        issuePage.uploadAttachment();
    }

    @Test(description = "6. Download Attachment",dependsOnMethods = { "uploadAttachment" }, groups = {"Sanity","Issues.Attachments" })
    public void downloadAttachment() throws IOException {
        issuePage.downloadAttachment();
        String resultMD5 = calculateHash(JiraVars.downloadFileLocation + JiraVars.attachmentFileName);
        assertEquals(resultMD5, expectedMD5);
    }
    @Test(description = "7. Create New User", dependsOnMethods = { "downloadAttachment" }, groups = {"Sanity","Issues.Attachments", })
    public void createUser () throws IOException, InterruptedException {
        createUser.createUsers();
    }
}

