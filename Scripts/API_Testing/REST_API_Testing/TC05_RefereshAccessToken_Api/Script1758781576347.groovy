import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import utils.GlobalAccessor as GA
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
import api.ApiKeywords as API
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
// Step 1: Prepare headers
KeywordUtil.logInfo("Preparing headers for refresh token request...")
def headers = [
    'Content-Type': 'application/json'
]
// Step 2: Prepare payload
KeywordUtil.logInfo("Reading refresh payload from JSON file...")
def payLoad = JsonOutput.toJson(JsonFileReader.getSectionData("refreshData", GlobalVariable.jsonDataFilePath))
KeywordUtil.logInfo("Payload prepared: " + payLoad)
// Step 3: Send POST request to refresh endpoint
KeywordUtil.logInfo("Sending POST request to /refresh endpoint...")
String baseUrl= GA.getProfileVariable(accessToken, apiBaseUrl)
def refreshResponse = API.sendPost(baseUrl,"refresh", payLoad, headers)
KeywordUtil.logInfo("Response received with status: " + refreshResponse.getStatusCode())
// Step 4: Parse response
def parsedResponse = new JsonSlurper().parseText(refreshResponse.getResponseBodyContent())
KeywordUtil.logInfo("Parsed response: " + parsedResponse.toString())
// Step 5: Extract new access token
def jwtToken = parsedResponse.accessToken
KeywordUtil.logInfo("Extracted new JWT Token: " + jwtToken)
// Step 6: Store token in JSON file
KeywordUtil.logInfo("Storing new JWT token in DummyJson section of JSON file...")
JsonFileReader.storeJsonData(GlobalVariable.responseDataFilePath, "DummyJson", "accessToken", jwtToken)
KeywordUtil.logInfo("Token stored successfully.")