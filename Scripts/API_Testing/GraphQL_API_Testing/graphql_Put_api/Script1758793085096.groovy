import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import api.ApiKeywords
import internal.GlobalVariable
import utils.GlobalAccessor
import utils.JsonFileReader
import utils.SchemaValidator

KeywordUtil.logInfo("Sending GraphQL mutation to update post with title and body")

def query = """
mutation UpdatePost(\$input: UpdatePostInput!, \$id: ID!) {
  updatePost(id: \$id, input: \$input) {
    id
    title
    body
  }
}
"""

def variables = [
  id: 1,
  input: [
    title: "A Very Captivating Post Title",
    body: "Some interesting content."
  ]
]

def headers = [ 'Content-Type': 'application/json' ]
String baseUrl = GlobalAccessor.getProfileVariable(graphQL, apiBaseUrl)

def response = ApiKeywords.sendGraphQL(baseUrl, query, variables, headers)

WS.verifyResponseStatusCode(response, 200)
KeywordUtil.logInfo("GraphQL response status code verified as 200")

def responseBody = response.getResponseBodyContent()
def json = new JsonSlurper().parseText(responseBody)

KeywordUtil.logInfo("Updated Post ID: ${json.data.updatePost.id}")
KeywordUtil.logInfo("Updated Post Title: ${json.data.updatePost.title}")
KeywordUtil.logInfo("Updated Post Body: ${json.data.updatePost.body}")
SchemaValidator.validateJsonWithSchema(responseBody, JsonOutput.toJson(JsonFileReader.getSectionData("graphQL_Patch", GlobalVariable.schemaFilePath)))
KeywordUtil.markPassed("GraphQL updatePost response schema validation passed")