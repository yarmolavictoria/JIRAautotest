package hillelauto.jira;

import hillelauto.Tools;

public interface JiraVars {
    public static final String baseURL = "http://jira.hillel.it:8080/";
    public static final String userSettingsURL = "http://jira.hillel.it:8080/secure/admin/user/UserBrowser.jspa";
    public static final String username = "autorob";
    public static final String password = "forautotests";

    //create users
    public static final String newUsername = "test123_test";
    public static final String newPassword = "test";
    public static final String fullName = "Test User";
    public static final String userEmail = "test123@test.com";


    public static final String newIssueSummary = "AutoTest " + Tools.timestamp();
    public static final String attachmentFileLocation = "C:/Users/";
    public static final String attachmentFileName = "Example.png";
    public static final String downloadFileLocation = "C:/Users/Vika/Downloads/";
    public static final String downloads = "chrome://downloads/";
}