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

KeywordUtil.logInfo("Sending GraphQL mutation to delete post by ID")

def testData = JsonFileReader.getSectionData("graphQL_deletePost",  GlobalVariable.jsonDataFilePath)

def query = QueryBuilder.buildQuery(testData)
def variables = testData.variables
def headers = testData.headers ?: [:]

String baseUrl = GA.getProfileVariable(graphQL, apiBaseUrl)

def response = ApiKeywords.sendGraphQL(baseUrl, query, variables, headers)

WS.verifyResponseStatusCode(response, 200)
KeywordUtil.logInfo("GraphQL response status code verified as 200")