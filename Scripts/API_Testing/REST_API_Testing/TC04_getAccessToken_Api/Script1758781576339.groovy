import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import utils.GlobalAccessor as GA
import api.ApiKeywords as API
import groovy.json.JsonOutput
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
import utils.SchemaValidator
import org.openqa.selenium.Keys as Keys
import groovy.json.JsonSlurper
// Step 1: Prepare payload
KeywordUtil.logInfo("Reading payload from JSON file...")
def payLoad = JsonOutput.toJson(JsonFileReader.getSectionData("postPayload", GlobalVariable.jsonDataFilePath))
KeywordUtil.logInfo("Payload prepared: " + payLoad)
// Step 2: Send POST request
KeywordUtil.logInfo("Sending POST request to /login endpoint...")
String baseUrl= GA.getProfileVariable(accessToken, apiBaseUrl)
def response = API.sendPost(baseUrl,"login", payLoad)
KeywordUtil.logInfo("Response received with status: " + response.getStatusCode())
// Step 3: Extract response body
def responseBodyContent = response.getResponseBodyContent()
//def responseJsonString = JsonOutput.toJson(new JsonSlurper().parseText(responseBodyContent))
KeywordUtil.logInfo("Response body content:\n" + responseBodyContent)
// Step 4: Validate response against schema
KeywordUtil.logInfo("Validating response against accessTokenSchema...")
def schemaMap = JsonFileReader.getSectionData("accessTokenSchema", GlobalVariable.schemaFilePath)
def schemaString = JsonOutput.toJson(schemaMap)
SchemaValidator.validateJsonWithSchema(responseBodyContent, schemaString)
//WS.validateJsonAgainstSchema(responseBodyContent, schemaString)
KeywordUtil.logInfo("Schema validation passed.")
// Step 5: Parse response and extract JWT token
def parsedResponse = new JsonSlurper().parseText(responseBodyContent)
def jwtToken = parsedResponse.accessToken
KeywordUtil.logInfo("Extracted JWT Token: " + jwtToken)
// Step 6: Store token in JSON file
KeywordUtil.logInfo("Storing JWT token in DummyJson section of JSON file...")
JsonFileReader.storeJsonData(GlobalVariable.responseDataFilePath, "DummyJson", "accessToken", jwtToken)
KeywordUtil.logInfo("Token stored successfully.")