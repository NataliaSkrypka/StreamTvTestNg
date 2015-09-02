package ua.net.streamtv.pages;

import com.google.inject.Inject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

/**
 * Created by nskrypka on 8/31/2015.
 */
public abstract class GeneralPage {

    private WebDriver driver;

    @Inject
    public GeneralPage (WebDriver driver){
        this.driver = driver;
    }

    protected WebElement waitForElementPresence (By locator, int timeoutInSeconds){
        new WebDriverWait(driver, timeoutInSeconds * 1000).until(presenceOfElementLocated(locator));
        return driver.findElement(locator);
    }

    protected void waitForAbsenceOfElement (By locator, int timeoutInSeconds){
        for (int i = 0; i < timeoutInSeconds * 2; i++){
            new WebDriverWait(driver, 500);
            if (driver.findElements(locator).isEmpty()){
                break;
            }
        }
    }

    public byte[] takeScreenshot(){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
