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
KeywordUtil.logInfo("Sending GraphQL mutation to create a new post using stored GET response")

def testData = JsonFileReader.getSectionData("graphQL_createPost", GlobalVariable.jsonDataFilePath)
def query = QueryBuilder.buildQuery(testData)
def headers = testData.headers ?: [:]

def storedData = JsonFileReader.getSectionData("get_GraphQL_Response",  GlobalVariable.responseDataFilePath)
def postData = storedData.data.post
def variables = [
  input: [
    title: postData.title,
    body: postData.body
  ]
]
String baseUrl = GA.getProfileVariable(graphQL, apiBaseUrl)
def response = ApiKeywords.sendGraphQL(baseUrl, query, variables, headers)
WS.verifyResponseStatusCode(response, 200)
KeywordUtil.logInfo("GraphQL response status code verified as 200")
def responseBody = response.getResponseBodyContent()
def json = new JsonSlurper().parseText(responseBody)
KeywordUtil.logInfo("Created Post ID: ${json.data.createPost.id}")
KeywordUtil.logInfo("Created Post Title: ${json.data.createPost.title}")
SchemaValidator.validateJsonWithSchema(responseBody, JsonOutput.toJson(JsonFileReader.getSectionData("graphQL_Post",GlobalVariable.schemaFilePath)))
KeywordUtil.markPassed("GraphQL createPost response schema validation passed")