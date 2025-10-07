package pages

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import utils.Helper as helper
public class UserRegistrationPage {
	@Keyword
	def registrationDetails(TestObject to, String value, String message, FailureHandling flowControl) {
		helper.sendKeys(to, value, message, flowControl)
	}

	@Keyword
	def clickOnPolicyCheckBox(TestObject policyCheckboxObj, FailureHandling flowControl) {
		helper.clickElement(policyCheckboxObj, "Policy Checkbox", flowControl)
	}


	@Keyword
	def clickOnContinue(TestObject continueBtnObj, FailureHandling flowControl) {
		helper.clickElement(continueBtnObj, "Continue Button", flowControl)
	}

	@Keyword
	def verifyText(TestObject to, String expectedText, String message, FailureHandling flowControl) {
		helper.verifyText(to, expectedText, message, flowControl)
	}
}
