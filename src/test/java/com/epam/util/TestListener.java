package com.epam.util;

import com.epam.mail.BasicTest;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class TestListener extends TestListenerAdapter {
    private Logger logger = LogManager.getRootLogger();

    public void onTestStart(ITestResult iTestResult) {
        deleteDir(new File(".//target/screenshots/"));
    }

    public void onTestSuccess(ITestResult iTestResult) {

    }

    public void onTestFailure(ITestResult iTestResult) {
        saveScreenshot(iTestResult);
    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }

    private void saveScreenshot(ITestResult iTestResult){
        logger.error("Test is failed, go into take screenshot method");
        String methodName = iTestResult.getMethod().getMethodName() + "_" + getCurrentTimeAsString();
        TakesScreenshot driver=(TakesScreenshot) BasicTest.driver;
        File screenFile = driver.getScreenshotAs(OutputType.FILE);
        takePhoto(methodName,driver);
        try {
            FileUtils.copyFile(screenFile, new File(
                    ".//target/screenshots/"
                            + methodName +
                            ".jpg"));
            logger.info("Screenshot is saved successfully with name: " + methodName);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getLocalizedMessage());
        }
    }

    @Attachment(value = "Failure in method {0}"+", Failure screenshot as below：",type = "image/png")
    public byte[]  takePhoto(String methodName ,TakesScreenshot driver){
        byte[] screenshotAs = driver.getScreenshotAs(OutputType.BYTES);
        return screenshotAs;
    }

    private String getCurrentTimeAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu-MM-dd_HH-mm-ss" );
        return ZonedDateTime.now().format(formatter);
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
