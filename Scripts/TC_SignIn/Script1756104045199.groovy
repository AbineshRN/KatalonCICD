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
import pages.AccountPage as AccountPage
import pages.HomePage as HomePage
import pages.LoginPage as LoginPage
import utils.JsonFileReader as JsonFileReader
import utils.Helper as helper
import com.report.ExtentManager as ExtentManager
try {
    KeywordUtil.logInfo('Navigating to the sign in page')
    CustomKeywords.'pages.HomePage.navigateToAccountPage'(FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('Navigated to the sign in page')

    KeywordUtil.logInfo('Signing into the account')
    List<Map<String, String>> loginFields = [
        [id: 'input-email', jsonKey: 'email'],
        [id: 'input-password', jsonKey: 'password']
    ]
    loginFields.each {
        CustomKeywords.'pages.LoginPage.inputField'(
            findTestObject('Object Repository/LoginPage/generic_Input', [('id') : it.id]),
            JsonFileReader.getJsonData('loginPage', it.jsonKey, GlobalVariable.jsonDataFilePath),
            it.jsonKey,
            FailureHandling.STOP_ON_FAILURE
        )
    }
    CustomKeywords.'pages.LoginPage.clickOnLoginButton'(findTestObject('Object Repository/LoginPage/Login_button'), FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('Signed into the account')

    KeywordUtil.logInfo('Verifying My Account')
    CustomKeywords.'pages.AccountPage.verifyMyAccount'(
        findTestObject('Object Repository/AccountPage/my_Account_verification'),
        JsonFileReader.getJsonData(section, 'verification_text', GlobalVariable.jsonDataFilePath),
        FailureHandling.STOP_ON_FAILURE
    )
    KeywordUtil.markPassed('Verified My Account')

} catch (Exception e) {
    KeywordUtil.markFailed('Signin testcase failed: ' + e.message)
    WebUI.takeScreenshot()
}