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
import utils.Helper as helper
import internal.GlobalVariable

public class OrderReturnPage {
	@Keyword
	def submittingAnOrderReturn(FailureHandling flowControl) {
		helper.clickElement(findTestObject('Object Repository/ProductReturnsPage/orderErrorOption'), "order error option", flowControl)
		helper.clickElement(findTestObject('Object Repository/ProductReturnsPage/submitButton'), "submit button", flowControl)
	}

	@Keyword
	def verifyOrderReturn(TestObject to, String expectedText, FailureHandling flowControl) {
		helper.verifyText(to, expectedText, "Order return", flowControl)
	}
}
