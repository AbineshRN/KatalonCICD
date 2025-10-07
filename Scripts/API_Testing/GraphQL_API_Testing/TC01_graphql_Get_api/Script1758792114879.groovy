import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import api.ApiKeywords
import internal.GlobalVariable
import utils.GlobalAccessor as GA
import utils.JsonFileReader
import utils.QueryBuilder
import utils.SchemaValidator
import utils.loadProfileVariables

KeywordUtil.logInfo("Sending GraphQL query to fetch post by ID")


def testData = JsonFileReader.getSectionData("graphQL_getPost", GlobalVariable.jsonDataFilePath)
def query = QueryBuilder.buildQuery(testData)
def variables = testData.variables
def headers = testData.headers ?: [:]

def profileVariables = loadProfileVariables.loadAllProfileVariables("graphQL")

def response = ApiKeywords.sendGraphQL(profileVariables["apiBaseUrl"], query, variables, headers)
WS.verifyResponseStatusCode(response, 200)
KeywordUtil.logInfo("GraphQL response status code verified as 200")

def responseBody = response.getResponseBodyContent()
def json = new JsonSlurper().parseText(responseBody)
println(responseBody)

KeywordUtil.logInfo("Post ID: ${json.data.post.id}")
KeywordUtil.logInfo("Post Title: ${json.data.post.title}")
SchemaValidator.validateJsonWithSchema(responseBody, JsonOutput.toJson(JsonFileReader.getSectionData("graphQL_GET", GlobalVariable.schemaFilePath)))
KeywordUtil.markPassed("GraphQL response schema validation passed")

JsonFileReader.storeApiResponse("get_GraphQL_Response", response, GlobalVariable.responseDataFilePath )