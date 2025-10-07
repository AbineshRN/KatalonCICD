import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import utils.GlobalAccessor as GA
import api.ApiKeywords as API
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil
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
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import utils.JsonFileReader
import utils.SchemaValidator

import org.openqa.selenium.Keys as Keys

def credentialId=JsonFileReader.getJsonData("postData", "CredentialId", GlobalVariable.responseDataFilePath)
KeywordUtil.logInfo("Sending GET request to /credentials/"+credentialId)
String baseUrl= GA.getProfileVariable(apiTesting, apiBaseUrl)
def getResponse = API.sendGet(baseUrl,"/credentials/"+credentialId)
WS.verifyResponseStatusCode(getResponse, 200)
KeywordUtil.logInfo("GET response status code verified as 200")

def getResponseBody = getResponse.getResponseBodyContent()
WS.validateJsonAgainstSchema(getResponseBody, JsonOutput.toJson(JsonFileReader.getSectionData("postResponse", masterSchemaFilePath)))
KeywordUtil.markPassed("GET response schema validation passed")

KeywordUtil.logInfo("Sending POST request to /credentials/ to replace full credential")
def postResponse = API.sendPost(baseUrl,"/credentials", getResponseBody)
WS.verifyResponseStatusCode(postResponse, 201)
SchemaValidator.validateJsonWithSchema(postResponse.getResponseBodyContent(), JsonOutput.toJson(JsonFileReader.getSectionData("postResponse", GlobalVariable.schemaFilePath)))
KeywordUtil.markPassed("POST full credential successfully and schema validated")