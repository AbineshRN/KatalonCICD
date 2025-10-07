import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import api.ApiKeywords
import internal.GlobalVariable
import utils.GlobalAccessor
import utils.JsonFileReader
import utils.QueryBuilder
import utils.SchemaValidator

KeywordUtil.logInfo("Sending GraphQL mutation to update post")


def testData = JsonFileReader.getSectionData("graphQL_updatePost", GlobalVariable.jsonDataFilePath)
def query = QueryBuilder.buildQuery(testData)
def variables = testData.variables
def headers = testData.headers ?: [:]


String baseUrl = GlobalAccessor.getProfileVariable(graphQL, apiBaseUrl)

def response = ApiKeywords.sendGraphQL(baseUrl, query, variables, headers)

WS.verifyResponseStatusCode(response, 200)
KeywordUtil.logInfo("GraphQL response status code verified as 200")

def responseBody = response.getResponseBodyContent()
def json = new JsonSlurper().parseText(responseBody)

KeywordUtil.logInfo("Updated Post ID: ${json.data.updatePost.id}")
KeywordUtil.logInfo("Updated Post Title: ${json.data.updatePost.title}")
SchemaValidator.validateJsonWithSchema(responseBody, JsonOutput.toJson(JsonFileReader.getSectionData("graphQL_Patch",GlobalVariable.schemaFilePath)))
KeywordUtil.markPassed("GraphQL updatePost response schema validation passed")