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
import utils.JsonFileReader as JsonFileReader
import org.openqa.selenium.Keys as Keys
import com.report.ExtentManager as ExtentManager

try {
    KeywordUtil.logInfo('Navigating to the registration page')
    CustomKeywords.'pages.HomePage.navigateToRegisterPage'(FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('Navigated to the registration page')

    KeywordUtil.logInfo('Filling in registration details')
    List<Map<String, String>> registrationFields = [
        [id: 'input-firstname', jsonKey: 'firstName'],
        [id: 'input-lastname', jsonKey: 'lastName'],
        [id: 'input-email', jsonKey: 'email'],
        [id: 'input-telephone', jsonKey: 'telephone'],
        [id: 'input-password', jsonKey: 'password'],
        [id: 'input-confirm', jsonKey: 'password']
    ]
    registrationFields.each {
        CustomKeywords.'pages.UserRegistrationPage.registrationDetails'(
            findTestObject('Object Repository/RegistrationPage/generic_Input', [('id') : it.id]),
            JsonFileReader.getJsonData('registerPage', it.jsonKey, GlobalVariable.jsonDataFilePath),
            it.jsonKey,
            FailureHandling.STOP_ON_FAILURE)
    }

    CustomKeywords.'pages.UserRegistrationPage.clickOnPolicyCheckBox'(
        findTestObject("Object Repository/RegistrationPage/policy_CheckBox"),
        FailureHandling.STOP_ON_FAILURE
    )

    CustomKeywords.'pages.UserRegistrationPage.clickOnContinue'(
        findTestObject("Object Repository/RegistrationPage/continue_Button"),
        FailureHandling.STOP_ON_FAILURE
    )

    KeywordUtil.markPassed('Submitted registration form')

    KeywordUtil.logInfo('Verifying registration success')
    CustomKeywords.'pages.UserRegistrationPage.verifyText'(findTestObject('Object Repository/AccountCreatedPage/account_Creation_Verification'),
        JsonFileReader.getJsonData('registerPage', 'successMessage', GlobalVariable.jsonDataFilePath),"Text Verified"
		, FailureHandling.STOP_ON_FAILURE)

    KeywordUtil.markPassed('User registration successful')
} catch (Exception e) {
     KeywordUtil.markFailed('User registration test case failed: ' + e.message)
    WebUI.takeScreenshot()
}