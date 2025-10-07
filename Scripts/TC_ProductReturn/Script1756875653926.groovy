import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import utils.JsonFileReader as JsonFileReader
import com.report.ExtentManager as ExtentManager
import utils.DependencyHandler

DependencyHandler.checkDependency("Sign_In", "ProductReturn")

try {
    KeywordUtil.logInfo('Signing into the account')
    WebUI.callTestCase(findTestCase('Test Cases/TC_SignIn'), [:])
    KeywordUtil.markPassed('Signed into the account')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to sign into the account: ' + e.message)
}

try {
    KeywordUtil.logInfo('Navigating to order history page')
    List<Map<String, String>> sideBarElements = [
        [text: ' Order History', label: 'Order History']
    ]
    sideBarElements.each {
        CustomKeywords.'pages.AccountPage.clickOnSideBarElements'(
            findTestObject('Object Repository/AccountPage/sideBar', [('text') : it.text]),
            it.label,
            FailureHandling.STOP_ON_FAILURE
        )
    }
    KeywordUtil.markPassed('Navigated to order history page')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to order history page: ' + e.message)
}

try {
    KeywordUtil.logInfo('Navigating to the order information page')
    CustomKeywords.'pages.OrderHistoryPage.clickOnViewButton'(
        findTestObject('Object Repository/OrderHistoryPage/viewInformationButton'),
        FailureHandling.STOP_ON_FAILURE
    )
    KeywordUtil.markPassed('Navigated to the order information page')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to order information page: ' + e.message)
}

try {
    KeywordUtil.logInfo('Navigating to the order return page')
    CustomKeywords.'pages.OrderInformationPage.clickOnReturnButton'(
        findTestObject('Object Repository/OrderInformationPage/returnButton'),
        FailureHandling.STOP_ON_FAILURE
    )
    CustomKeywords.'pages.OrderReturnPage.submittingAnOrderReturn'(FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('Navigated to the order return page')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to order return page: ' + e.message)
}

try {
    KeywordUtil.logInfo('Verifying the order return')
    CustomKeywords.'pages.OrderReturnPage.verifyOrderReturn'(
        findTestObject('Object Repository/ProductReturnsPage/verifyText'),
        JsonFileReader.getJsonData(section, 'verifyText', GlobalVariable.jsonDataFilePath),
        FailureHandling.STOP_ON_FAILURE
    )
    KeywordUtil.markPassed('Verified the order return')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to verify the order return: ' + e.message)
}