import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import utils.GlobalAccessor as GA
import api.ApiKeywords as API
import groovy.json.JsonSlurper
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
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import utils.JsonFileReader

import org.openqa.selenium.Keys as Keys
// Step 1: Read access token from JSON file
KeywordUtil.logInfo("Reading access token from JSON file...")
def accessToken = JsonFileReader.getJsonData("DummyJson", "accessToken", GlobalVariable.responseDataFilePath)
KeywordUtil.logInfo("Access token retrieved: " + accessToken)

// Step 2: Prepare headers
def headers = [
    "Authorization": accessToken,
    "Content-Type": "application/json"
]
KeywordUtil.logInfo("Headers prepared: " + headers.toString())

// Step 3: Send GET request to /me endpoint
KeywordUtil.logInfo("Sending GET request to /me endpoint...")
String baseUrl= GA.getProfileVariable(accessTokenUrl, apiBaseUrl)
def response = API.sendGet(baseUrl,"me", headers)
KeywordUtil.logInfo("Response received with status: " + response.getStatusCode())

// Step 4: Parse response
def parsedResponse = new JsonSlurper().parseText(response.getResponseBodyContent())
KeywordUtil.logInfo("Parsed response: " + parsedResponse.toString())

// Step 5: Store firstName in JSON file
KeywordUtil.logInfo("Storing firstName in apiResponse section of JSON file...")
JsonFileReader.storeJsonData(GlobalVariable.responseDataFilePath, "apiResonse", "firstName", parsedResponse.firstName)
KeywordUtil.logInfo("firstName stored successfully: " + parsedResponse.firstName)

// Step 6: Store full response under userData section
KeywordUtil.logInfo("Storing full response under userData section...")
JsonFileReader.storeApiResponse("userData", response, GlobalVariable.responseDataFilePath)
KeywordUtil.logInfo("Full response stored successfully.")