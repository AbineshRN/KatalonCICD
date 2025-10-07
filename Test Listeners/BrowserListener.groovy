import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import utils.SkipThisTestExecution
import internal.GlobalVariable
import utils.DependencyHandler
import utils.JsonFileReader

class BrowserListener {
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
    def beforeTestCase(TestCaseContext testCaseContext) {
        def testCaseId = testCaseContext.getTestCaseId()
        def testCaseName = testCaseId.split('/').last()

        if (testCaseName.toLowerCase().endsWith("api")) {
            KeywordUtil.logInfo("Skipping browser launch for API test case: ${testCaseName}")
            return
        }
        def dependencyMap = DependencyHandler.getDependencyMap()
        if (dependencyMap.containsKey(testCaseName)) {
            def baseTestCase = dependencyMap[testCaseName]
            try {
                DependencyHandler.checkDependency(baseTestCase, testCaseName)
            } catch (SkipThisTestExecution e) {
                KeywordUtil.markWarning(e.message)
                testCaseContext.skipThisTestCase()
                return
            }
        }
        WebUI.openBrowser('')
        WebUI.navigateToUrl(GlobalVariable.baseUrl)
        WebUI.maximizeWindow()
    }
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def closeTheBrowser(TestCaseContext testCaseContext) {
		def testCaseId = testCaseContext.getTestCaseId()
		def testCaseName = testCaseId.split('/').last()
		def testCaseStatus = testCaseContext.getTestCaseStatus()
		println(testCaseStatus)
		def StatusMap = DependencyHandler.getStatusMap()
		if(StatusMap.containsKey(testCaseName)) {
			JsonFileReader.updateJsonValue('executionStatus', testCaseName, testCaseStatus, GlobalVariable.jsonDataFilePath)
		}
		try {
			def driver = DriverFactory.getWebDriver()
			if (driver != null) {
				WebUI.closeBrowser()
				KeywordUtil.logInfo("Browser closed successfully.")
			} else {
				KeywordUtil.logInfo("No browser session to close.")
			}
		} catch (Exception e) {
			KeywordUtil.logInfo("Error while closing browser: ${e.message}")
		}
	}
}