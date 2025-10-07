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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import utils.JsonFileReader
import utils.SchemaValidator
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.Keys as Keys
import api.ApiKeywords as API
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS


KeywordUtil.logInfo("Sending POST request to /credentials to create new credentials")
def postPayload = JsonOutput.toJson(JsonFileReader.getSectionData("loginPage", GlobalVariable.jsonDataFilePath))
println(postPayload)

String baseUrl = GA.getProfileVariable(apiTesting, apiBaseUrl)
def postResponse = API.sendPost(baseUrl,"/credentials", postPayload)

WS.verifyResponseStatusCode(postResponse, 201)
KeywordUtil.logInfo("POST response status code verified as 201")

def postResponseBody = postResponse.getResponseBodyContent()
println(postResponseBody)
def createdCredential = new JsonSlurper().parseText(postResponseBody)
println(createdCredential)
def credentialId = createdCredential.id
JsonFileReader.storeJsonData(GlobalVariable.responseDataFilePath, "postData", "CredentialId", credentialId)
KeywordUtil.logInfo("Created credential ID: " + credentialId)
SchemaValidator.validateJsonWithSchema(postResponseBody, JsonOutput.toJson(JsonFileReader.getSectionData("postResponse", GlobalVariable.schemaFilePath)))
KeywordUtil.markPassed("POST response schema validation passed")