package com.selgp.opensauce;

import com.selgp.opensauce.appium.BaseAppiumTest;
import com.selgp.opensauce.automation.core.saucelabs.SauceREST;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guillemhernandezsola on 19/04/14.
 */
public class BaseSauceLabs extends BaseAppiumTest {

    public List<Integer> values;
    public static final int MINIMUM = 0;
    public static final int MAXIMUM = 10;


    public final static String USER = System.getProperty("user", "unset");
    public final static String APIKEY = System.getProperty("apikey", "unset");
    public SauceREST client = new SauceREST(USER, APIKEY);
    private InheritableThreadLocal<WebDriver> globalDriver = new InheritableThreadLocal<WebDriver>();
    private String sessionId;
    private String testName;


    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USER_NAME or SAUCE_USER_NAME environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @BeforeMethod(alwaysRun = true)
    protected void setup(Method method, Object[] testArguments) {
        // set up appium
        testName = method.getName() + "_" + testArguments.hashCode();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("id", testName);
        capabilities.setCapability("name", testName);
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(CapabilityType.VERSION, "6.0");
        capabilities.setCapability("device", "iPhone Simulator");
        capabilities.setCapability(CapabilityType.PLATFORM, "Mac 10.8");
        capabilities.setCapability("app", "http://appium.s3.amazonaws.com/TestApp6.0.app.zip");
        try {
            globalDriver.set(new RemoteWebDriver(new URL("http://" + USER + ":" + APIKEY + "@ondemand.saucelabs.com:80/wd/hub"),
                    capabilities));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        sessionId = ((RemoteWebDriver) globalDriver.get()).getSessionId().toString();
        values = new ArrayList<Integer>();
    }

    @AfterMethod(alwaysRun = true)
    protected void teardown(ITestResult tr, Method method) {
        globalDriver.get().quit();
        if (tr.isSuccess()) {
            client.jobPassed(sessionId);
        } else {
            client.jobFailed(sessionId);
        }
    }

    protected WebDriver driver() {
        return globalDriver.get();
    }
}
