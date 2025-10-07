import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.model.FailureHandling.STOP_ON_FAILURE
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
try {
    KeywordUtil.logInfo('Signing into the account')
    WebUI.callTestCase(findTestCase('Test Cases/TC_SignIn'), [:])
    KeywordUtil.markPassed('Signed into the account')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to sign into the account: ' + e.message)
}

try {
    KeywordUtil.logInfo('Navigating to change password page')
    CustomKeywords.'pages.AccountPage.clickOnChangePassword'(findTestObject('Object Repository/AccountPage/changePassword'), FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('Navigated to change password page')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to change password page: ' + e.message)
}

try {
    KeywordUtil.logInfo('Setting up a new password')
    CustomKeywords.'pages.PasswordChangePage.changingPassword'(JsonFileReader.getJsonData(section, 'password', GlobalVariable.jsonDataFilePath), FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('New password has been set successfully')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to set new password: ' + e.message)
}

try {
    KeywordUtil.logInfo('Verifying Password Change Text')
    CustomKeywords.'pages.AccountPage.verifyPasswordChange'(findTestObject('Object Repository/AccountPage/passwordChangedText'), JsonFileReader.getJsonData(section, 'verifyText', filepath), FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('Verified Password Change Text')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to verify password change text: ' + e.message)
}

try {
    KeywordUtil.logInfo('Updating login password in JSON file')
    JsonFileReader.updateJsonValue('loginPage', 'password', JsonFileReader.getJsonData(section, 'password', GlobalVariable.jsonDataFilePath), GlobalVariable.jsonDataFilePath)
    KeywordUtil.markPassed('Updated login password in JSON file')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to update password in JSON file: ' + e.message)
}