package pages

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.eclipse.persistence.internal.helper.Helper

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

public class HomePage {
	@Keyword
	def navigateToAccountPage(FailureHandling flowControl) {
		helper.hoverElement(findTestObject('Object Repository/HomePage/my_Account_Button'), "my account", flowControl)
		helper.clickElement(findTestObject('Object Repository/HomePage/login_Button'), "login button", flowControl)
	}

	@Keyword
	def navigateToRegisterPage(FailureHandling flowControl) {
		helper.hoverElement(findTestObject('Object Repository/HomePage/my_Account_Button'), "my account", flowControl)
		helper.clickElement(findTestObject('Object Repository/HomePage/register_Button'), "Register button", flowControl)
	}


	@Keyword
	def clickOnShopByCategoryOptions(String categoryText, FailureHandling flowControl) {
		helper.clickElement(findTestObject('Object Repository/HomePage/shopByCategory'), "Shop by category", flowControl)
		helper.clickElement(findTestObject('Object Repository/HomePage/dynamicShopByCategory', [('text') : categoryText]), "Components", flowControl)
	}


	@Keyword
	def clickOnBlog(TestObject blogObj, String message, FailureHandling flowControl) {
		helper.clickElement(blogObj, message, flowControl)
	}
}
