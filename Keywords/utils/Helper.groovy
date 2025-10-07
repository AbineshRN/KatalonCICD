package utils


import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import org.openqa.selenium.WebDriver
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.report.ExtentManager
import java.awt.Robot
import java.awt.Rectangle
import java.awt.Toolkit
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
class Helper {
	@Keyword
	static def refreshBrowser() {
		try {
			com.kms.katalon.core.util.KeywordUtil.logInfo("ℹ️ Refreshing browser")
			WebDriver webDriver = DriverFactory.getWebDriver()
			webDriver.navigate().refresh()
			com.kms.katalon.core.util.KeywordUtil.markPassed("✅ Browser refreshed successfully")
		} catch (Exception e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ Failed to refresh browser: " + e.message)
			WebUI.takeScreenshot()
			throw e
		}
	}

	@Keyword
	static def clickElement(TestObject to, String message, FailureHandling flowControl) {
		try {
			WebUI.waitForElementVisible(to, 10, flowControl)
			com.kms.katalon.core.util.KeywordUtil.logInfo("ℹ️ Clicking on: " + message)
			WebUI.click(to, flowControl)
			com.kms.katalon.core.util.KeywordUtil.markPassed("✅ " + message + " clicked successfully")
		} catch (WebElementNotFoundException e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ " + message + " not found: " + e.message)
			if (DriverFactory.getWebDriver() != null) WebUI.takeScreenshot()
			throw e
		} catch (Exception e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ Failed to click on " + message + ": " + e.message)
			if (DriverFactory.getWebDriver() != null) WebUI.takeScreenshot()
			throw e
		}
	}

	@Keyword
	static def hoverElement(TestObject to, String message, FailureHandling flowControl) {
		try {
			WebUI.waitForElementVisible(to, 10, flowControl)
			com.kms.katalon.core.util.KeywordUtil.logInfo("ℹ️ Hovering over: " + message)
			WebUI.mouseOver(to, flowControl)
			com.kms.katalon.core.util.KeywordUtil.markPassed("✅ " + message + " hovered successfully")
		} catch (WebElementNotFoundException e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ " + message + " not found: " + e.message)
			if (DriverFactory.getWebDriver() != null) WebUI.takeScreenshot()
			throw e
		} catch (Exception e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ Failed to hover on " + message + ": " + e.message)
			if (DriverFactory.getWebDriver() != null) WebUI.takeScreenshot()
			throw e
		}
	}

	@Keyword
	static def sendKeys(TestObject to, String value, String message, FailureHandling flowControl) {
		try {
			WebUI.waitForElementVisible(to, 10, flowControl)
			com.kms.katalon.core.util.KeywordUtil.logInfo("ℹ️ Entering data into: " + message)
			WebUI.setText(to, value, flowControl)
			com.kms.katalon.core.util.KeywordUtil.markPassed("✅ " + message + " entered successfully")
		} catch (WebElementNotFoundException e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ " + message + " not found: " + e.message)
			if (DriverFactory.getWebDriver() != null) WebUI.takeScreenshot()
			throw e
		} catch (Exception e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ Failed to enter data into " + message + ": " + e.message)
			if (DriverFactory.getWebDriver() != null) WebUI.takeScreenshot()
			throw e
		}
	}

	@Keyword
	static def verifyText(TestObject to, String expected, String message, FailureHandling flowControl) {
		try {
			WebUI.waitForElementVisible(to, 10, flowControl)
			com.kms.katalon.core.util.KeywordUtil.logInfo("ℹ️ Verifying text for: " + message)
			WebUI.verifyElementText(to, expected, flowControl)
			com.kms.katalon.core.util.KeywordUtil.markPassed("✅ " + message + " verified successfully")
		} catch (WebElementNotFoundException e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ " + message + " not found: " + e.message)
			if (DriverFactory.getWebDriver() != null) WebUI.takeScreenshot()
			throw e
		} catch (Exception e) {
			com.kms.katalon.core.util.KeywordUtil.markFailed("❌ Failed to verify text for " + message + ": " + e.message)
			if (DriverFactory.getWebDriver() != null) WebUI.takeScreenshot()
			throw e
		}
	}

	static void captureScreenshot(String filePath) {
		BufferedImage image = new Robot().createScreenCapture(
				new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()))
		ImageIO.write(image, "jpg", new File(filePath))
	}

	static def writeGetResponseToFile(def response, String filePath) {
		def responseBody = response.getResponseBodyContent()
		def jsonData = new JsonSlurper().parseText(responseBody)

		def file = new File(filePath)
		if (!file.parentFile.exists()) {
			file.parentFile.mkdirs()
		}

		def jsonText = JsonOutput.prettyPrint(JsonOutput.toJson(jsonData))
		file.write(jsonText)
	}
}


