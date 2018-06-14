package hillelauto.jira;

import hillelauto.Tools;

public interface JiraVars {
    public static final String baseURL = "http://jira.hillel.it:8080/";
    public static final String username = "yarmolavictoria";
    public static final String password = "qwerty123";

    public static final String newIssueSummary = "AutoTest " + Tools.timestamp();
    public static final String attachmentFileLocation = "C:/Users/";
    public static final String attachmentFileName = "Example.png";
    public static final String downloadFileLocation = "C:/Users/Vika/Downloads/";
    public static final String downloads = "chrome://downloads/";
}