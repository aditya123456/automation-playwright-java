package uipages;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import utils.ConfigurationReader;

import java.util.HashMap;

public class LoginPage {

    protected String URL = ConfigurationReader.getProperty("url");

    /*
    Currently Ii have hardcoded users in config.properties file but in real world scenario,
    we can keep users and password in keyvault or gitlab/github enviroment variable to avoid the security issue.

     */

    protected String USER_NAME =  ConfigurationReader.getProperty("username_standard");
    protected String USER_LOCKED = ConfigurationReader.getProperty("username_locked_out_user");

    protected String USER_PROBLEM = ConfigurationReader.getProperty("username_problem_user");

    protected String USER_PERFORMANCE = ConfigurationReader.getProperty("username_performance_glitch_user");

    protected String USER_ERROR = ConfigurationReader.getProperty("username_error_user");

    protected String USER_VISUAL = ConfigurationReader.getProperty("username_visual_user");

    private final HashMap<String, String> userTypes = new HashMap<>();

    {
        userTypes.put("standard", USER_NAME);
        userTypes.put("locked", USER_LOCKED);
        userTypes.put("problem", USER_PROBLEM);
        userTypes.put("performance", USER_PERFORMANCE);
        userTypes.put("error", USER_ERROR);
        userTypes.put("visual", USER_VISUAL);
    }


    protected String PASSWORD = ConfigurationReader.getProperty("password");

    public void navigate(String url){
        page.navigate(url);
    }


    private final Locator username;

    private final Locator password;

    private final Locator loginButton;

    private final Locator loginError;

    private final Page page;


    public LoginPage( Page page) {
        this.loginError = page.locator("div.error-message-container.error > h3");
        this.page = page;
        this.username = page.locator("[data-test=\"username\"]");
        this.password = page.locator("[data-test=\"password\"]");
        this.loginButton = page.locator("[data-test=\"login-button\"]");
    }

    public void login(String userType)
    {
        String userName = userTypes.getOrDefault(userType, "standard");
        navigate(URL);
        username.fill(userName);
        password.fill(PASSWORD);
        loginButton.click();
    }

    public String loginErrorMessage(){
        return loginError.textContent();
    }
}
