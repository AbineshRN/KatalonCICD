package com.report
//
//import com.aventstack.extentreports.ExtentReports
//import com.aventstack.extentreports.ExtentTest
//import com.aventstack.extentreports.reporter.ExtentSparkReporter
//import com.aventstack.extentreports.Status
//
//import org.openqa.selenium.OutputType
//import org.openqa.selenium.TakesScreenshot
//import org.openqa.selenium.WebDriver
//import com.kms.katalon.core.webui.driver.DriverFactory
//
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import com.kms.katalon.core.util.KeywordUtil
//import internal.GlobalVariable
//
//public class ExtentManager {
//
//	private static ExtentReports extent
//	private static ExtentTest test
//	private static ExtentSparkReporter spark
//
//	// Initialize the report
//	public static void initReport(String reportName) {
//		File reportDir = new File("Reports")
//		if (!reportDir.exists()) {
//			reportDir.mkdirs()
//		}
//		String timestamp = new Date().format("yyyyMMdd_HHmmss")
//		spark = new ExtentSparkReporter(reportDir.getPath() + "/" + reportName + "_" + timestamp + ".html")
//		extent = new ExtentReports()
//		extent.attachReporter(spark)
//	}
//
//
//	// Create a test case entry
//	public static void createTest(String testName) {
//		test = extent.createTest(testName)
//	}
//
//	// Log info
//	public static void logInfo(String message) {
//		test.log(Status.INFO, message)
//	}
//
//	// Log warning
//	public static void logWarning(String message) {
//		test.log(Status.WARNING, message)
//	}
//
//	// Log pass
//	public static void logPass(String message) {
//		test.log(Status.PASS, message)
//	}
//
//	// Log fail with screenshot
//	public static void logFail(String message) {
//		try {
//			WebDriver driver = DriverFactory.getWebDriver()
//			String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64)
//			test.fail(message).addScreenCaptureFromBase64String(base64Screenshot, "Screenshot")
//		} catch (Exception e) {
//			test.fail("Failed to capture screenshot: " + e.message)
//		}
//	}
//
//	// Log skip
//	public static void logSkip(String message) {
//		test.log(Status.SKIP, message)
//	}
//
//	// Flush the report
//	public static void flushReport() {
//		extent.flush()
//	}
//}
