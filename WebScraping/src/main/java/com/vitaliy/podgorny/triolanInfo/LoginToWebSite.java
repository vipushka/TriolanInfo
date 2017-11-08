package com.vitaliy.podgorny.triolanInfo;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by ViP on 07.11.2017.
 * this class for login to web page and return after login page
 */
public class LoginToWebSite {
    private static Logger logger = Logger.getLogger(LoginToWebSite.class);
    //Create a new WebClient with any BrowserVersion. WebClient belongs to the HtmlUnit library.
    private final WebClient WEB_CLIENT = new WebClient(BrowserVersion.CHROME);

    //This is pretty self explanatory, these are your Reddit credentials.
    private final String username;
    private final String password;

    //Our constructor. Sets our username and password and does some client config.
    LoginToWebSite(String username, String password) {
        this.username = username;
        this.password = password;
        //Retreives our WebClient's cookie manager and enables cookies.
        //This is what allows us to view pages that require login.
        //If this were set to false, the login session wouldn't persist.
        WEB_CLIENT.getCookieManager().setCookiesEnabled(true);
    }

    public void login(String loginURL) {
        //This is the URL where we log in, easy.
        try {
            //Okay, bare with me here. This part is simple but it can be tricky
            //to understand at first. Reference the login form above and follow
            //along.
            //Create an HtmlPage and get the login page.
            logger.info("Get login page");
            HtmlPage loginPage = WEB_CLIENT.getPage(loginURL);
            //Create an HtmlForm by locating the form that pertains to logging in.
            //"//form[@id='login-form']" means "Hey, look for a <form> tag with the
            //id attribute 'login-form'" Sound familiar?
            //<form id="login-form" method="post" ...

            //------------------>HtmlForm loginForm = loginPage.getFirstByXPath("//form[@id='login-form']");

            //There is no form with id on triolan page use getFroms()
            logger.info("Get form");
            HtmlForm loginForm = loginPage.getForms().get(0);

            //choose triolan radio button - I choose by dogovor
            //get(1) phone
            //get(2) Email
            logger.info("Choose 'by dogovor'");
            loginForm.getInputsByName("").get(0).setDefaultValue("1");

            //This is where we modify the form. The getInputByName method looks
            //for an <input> tag with some name attribute. For example, user or passwd.
            //If we take a look at the form, it all makes sense.
            //<input value="" name="user" id="user_login" ...
            //After we locate the input tag, we set the value to what belongs.
            //So we're saying, "Find the <input> tags with the names "user" and "passwd"
            //and throw in our username and password in the text fields.
            logger.info("Set login and password");
            loginForm.getInputByName("login2$tbAgreement").setValueAttribute(username);
            loginForm.getInputByName("login2$tbPassword").setValueAttribute(password);

            //<button type="submit" class="c-btn c-btn-primary c-pull-right" ...
            //Okay, you may have noticed the button has no name. What the line
            //below does is locate all of the <button>s in the login form and
            //clicks the first and only one. (.get(0)) This is something that
            //you can do if you come across inputs without names, ids, etc.
            logger.info("Click login button");
            loginForm.getInputByName("login2$btnLoginByAgr").click();
        } catch (FailingHttpStatusCodeException e) {
            logger.error(e.getMessage());
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String get(String URL) {
        try {
            logger.info("get html from need page");
            return WEB_CLIENT.getPage(URL).getWebResponse().getContentAsString();
        } catch (FailingHttpStatusCodeException e) {
            logger.error(e.getMessage());
        } catch (MalformedURLException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}