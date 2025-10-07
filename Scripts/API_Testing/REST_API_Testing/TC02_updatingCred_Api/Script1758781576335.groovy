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
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import internal.GlobalVariable
import utils.JsonFileReader
import utils.SchemaValidator

import org.openqa.selenium.Keys as Keys
import api.ApiKeywords as API
def credentialId=JsonFileReader.getJsonData("postData", "CredentialId", GlobalVariable.responseDataFilePath)
KeywordUtil.logInfo("Sending PATCH request to /credentials/"+credentialId+" to update password")
def patchPayload = JsonOutput.toJson([
    password: "NewPassword@456"
])
String baseUrl = GA.getProfileVariable(apiTesting, apiBaseUrl)
def patchResponse = API.sendPatch(baseUrl,"/credentials/"+credentialId, patchPayload)
WS.verifyResponseStatusCode(patchResponse, 200)
println(patchResponse.getResponseBodyContent())
SchemaValidator.validateJsonWithSchema(patchResponse.getResponseBodyContent(), JsonOutput.toJson(JsonFileReader.getSectionData("patchResponse", GlobalVariable.schemaFilePath)))
KeywordUtil.markPassed("PATCH password update successful")