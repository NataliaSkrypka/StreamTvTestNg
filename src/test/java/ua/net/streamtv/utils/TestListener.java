package ua.net.streamtv.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ua.net.streamtv.guiceConfiguration.GuiceTestClass;

/**
 * Created by nskrypka on 8/30/15.
 */
public class TestListener extends TestListenerAdapter {

    private Logger log;

    @Override
    public void onTestSuccess(ITestResult tr) {
        log = LoggerFactory.getLogger(tr.getInstanceName());
        log.info("************************************* "  + tr.getName() + " passed *****************************************");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        log = LoggerFactory.getLogger(tr.getInstanceName());
        log.info("///////////////////////////////////// " + tr.getName() + " failed /////////////////////////////////////////");
    }

    @Override
    public void onTestStart(ITestResult result) {
        log = LoggerFactory.getLogger(result.getInstanceName());
        log.info ("------------------------------------ " + result.getName() + " started -------------------------------------");
    }

    @Override
    public void onFinish(ITestContext testContext){

    }


}
