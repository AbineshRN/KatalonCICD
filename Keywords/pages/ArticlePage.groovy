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
public class ArticlePage {
	@Keyword
	def inputNameOnArticle(TestObject to, String name, FailureHandling flowControl) {
		helper.sendKeys(to, name, "Name", flowControl)
	}

	@Keyword
	def inputEmailOnArticle(TestObject to, String email, FailureHandling flowControl) {
		helper.sendKeys(to, email, "Email", flowControl)
	}

	@Keyword
	def articleDetails(TestObject to, String value, String message, FailureHandling flowControl) {
		helper.sendKeys(to, value, message, flowControl)
	}

	@Keyword
	def inputCommentOnArticle(TestObject to, String comment, FailureHandling flowControl) {
		helper.sendKeys(to, comment, "Comment", flowControl)
	}

	@Keyword
	def clickOnPostCommentButton(TestObject to, FailureHandling flowControl) {
		helper.clickElement(to, "Post comment", flowControl)
	}

	@Keyword
	def verifySuccessText(TestObject to, String successMessage, FailureHandling flowControl) {
		helper.verifyText(to, successMessage, "Success Text", flowControl)
	}
}
