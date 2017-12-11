package util;

import exception.MissingFileException;
import exception.UnsupportedDriverNameException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import parser.ProjectProperties;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    private static volatile DriverFactory instance;
    static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private WebDriver driver;
    private DesiredCapabilities capabilities;
    private URL url;

    private DriverFactory() throws MissingFileException, UnsupportedDriverNameException{
        driver = setupDriver();
    }

    public static DriverFactory getInstance() throws MissingFileException, UnsupportedDriverNameException{
        DriverFactory localInstance = instance;
        if (localInstance == null) {
            synchronized (DriverFactory.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DriverFactory();
                }
            }
        }
        return localInstance;
    }

    private WebDriver setupDriver() {
        String driverName = ProjectProperties.getProperties().getProperty("browser.name");
        String serverURL = ProjectProperties.getProperties().getProperty("server.url");
        switch (driverName) {
            case ("chrome") : {
                capabilities = DesiredCapabilities.chrome();
                initializeDriver(serverURL, capabilities);
                logger.log(Level.INFO, "Google Chrome webdriver set");
                break;
            }
            case ("firefox") : {
                capabilities = DesiredCapabilities.firefox();
                initializeDriver(serverURL, capabilities);
                logger.log(Level.INFO, "Firefox webdriver set");
                break;
            }
            case ("ie") : {
                capabilities = DesiredCapabilities.internetExplorer();
                initializeDriver(serverURL, capabilities);
                logger.log(Level.INFO, "Internet Explorer webdriver set");
                break;
            }
            case ("edge") : {
                capabilities = DesiredCapabilities.edge();
                initializeDriver(serverURL, capabilities);
                logger.log(Level.INFO, "Microsoft Edge webdriver set");
                break;
            }
            case ("safari") : {
                capabilities = DesiredCapabilities.safari();
                initializeDriver(serverURL, capabilities);
                logger.log(Level.INFO, "Safari webdriver set");
                break;
            }
            case ("localfirefox") : {
                capabilities = DesiredCapabilities.firefox();
                serverURL = "http://localhost:4444/wd/hub";
                initializeDriver(serverURL, capabilities);
                logger.log(Level.INFO, "Local Firefox webdriver set");
                break;
            }
            case ("localchrome") : {
                capabilities = DesiredCapabilities.chrome();
                serverURL = "http://localhost:4444/wd/hub";
                initializeDriver(serverURL, capabilities);
                logger.log(Level.INFO, "Local Chrome webdriver set");
                break;
            }
            default: {
                throw new UnsupportedDriverNameException("Can not load driver '" + driverName + "'");
            }
        }
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://booking.com");
        return driver;
    }


    private void initializeDriver(String serverURL, DesiredCapabilities capabilities) {
        this.capabilities = capabilities;
        try {
            url = new URL(serverURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver = new RemoteWebDriver(url, capabilities);
    }

    public WebDriver getDriver() {
        return driver;
    }
}