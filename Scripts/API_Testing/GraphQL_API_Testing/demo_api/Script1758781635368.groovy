import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
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

import api.ApiKeywords
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

def query = """
query GetCharacter(\$id: ID!) {
  character(id: \$id) {
    name
    status
    species
  }
}
"""

def variables = [ id: 1 ]
def headers = [ 'Content-Type': 'application/json' ]

def response = ApiKeywords.sendGraphQL(
  'https://rickandmortyapi.com/graphql',
  query,
  variables,
  headers
)

def json = new JsonSlurper().parseText(response.getResponseBodyContent())
println(JsonOutput.prettyPrint(JsonOutput.toJson(json)))
