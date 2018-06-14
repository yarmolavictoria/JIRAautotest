package hillelauto.jira;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import hillelauto.Tools;

public class IssuePage {
    private final By inputProject = By.id("project-field");
    private final By inputSummary = By.id("summary");
    private final WebDriver browser;
    private String newIssuePath;
    private String attachmentLink;
    @FindBy(css = "a#create_link")
    private WebElement buttonCreateIssue;
    @FindBy(css = "a.issue-created-key")
    private List<WebElement> linkNewIssues;
    @FindBy(css = "input.issue-drop-zone__file")
    private WebElement inputUploadAttachment;
    @FindBy(css = "a.attachment-title")
    private WebElement linkAttachmentName;

    public IssuePage(WebDriver browser) {
        this.browser = browser;
    }

    public void createIssue() {
        buttonCreateIssue.click();

        Tools.clearAndFill(inputProject, "General QA Robert (GQR)\n");
        // Tools.clearAndFill(inputSummary, JiraVars.newIssueSummary)
        new FluentWait<>(browser).withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(500))
                .ignoring(InvalidElementStateException.class)
                .until(browser -> Tools.clearAndFill(inputSummary, JiraVars.newIssueSummary)).submit();

        // ((JavascriptExecutor) browser).executeScript("window.scrollBy(0,250)");

        Assert.assertTrue(linkNewIssues.size() != 0);

        newIssuePath = linkNewIssues.get(0).getAttribute("href");
    }

    public void openIssue() {
        browser.get(newIssuePath);
        Assert.assertTrue(browser.getTitle().contains(JiraVars.newIssueSummary));
    }

    public boolean uploadAttachment() {
        inputUploadAttachment.sendKeys(JiraVars.attachmentFileLocation + JiraVars.attachmentFileName);

        new FluentWait<>(browser).withTimeout(Duration.ofSeconds(20)).pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class).until(browser -> linkAttachmentName);

        Assert.assertEquals(JiraVars.attachmentFileName, linkAttachmentName.getText());


        attachmentLink = linkAttachmentName.getAttribute("href");

        return JiraVars.attachmentFileName.equals(linkAttachmentName.getText());
    }

    public void downloadAttachment() throws IOException {
        URL website = new URL(attachmentLink);
        try (InputStream in = website.openStream()) {
            Path targetPath = new File(JiraVars.downloadFileLocation+JiraVars.attachmentFileName).toPath();
            Files.copy(in,targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}