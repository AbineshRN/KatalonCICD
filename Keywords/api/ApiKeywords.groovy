package api
import com.kms.katalon.core.testobject.ObjectRepository as OR
import utils.GlobalAccessor as GA
import com.kms.katalon.core.testobject.RestRequestObjectBuilder
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonOutput
import internal.GlobalVariable
public class ApiKeywords {
	private static List<TestObjectProperty> buildHeaders(Map<String, String> headers) {
		return headers.collect { key, value ->
			new TestObjectProperty(key, ConditionType.EQUALS, value)
		}
	}
	def static sendGet(String apiBaseUrl, String endpoint, Map<String, String> headers = [:]) {
		def baseObject = OR.findTestObject('Object Repository/API_Testing/GET_Generic', [
			'endpoint': apiBaseUrl + endpoint
		])
		if (headers && headers.size() > 0) {
			baseObject.setHttpHeaderProperties(buildHeaders(headers))
		}
		return WS.sendRequest(baseObject)
	}
	def static sendPost(String apiBaseUrl, String endpoint, def body, Map<String, String> headers = [:]) {
		def baseObject = OR.findTestObject('Object Repository/API_Testing/POST_Generic', [
			'endpoint': apiBaseUrl + endpoint,
			'body': body
		])
		if (headers && headers.size() > 0) {
			baseObject.setHttpHeaderProperties(buildHeaders(headers))
		}
		return WS.sendRequest(baseObject)
	}

	def static sendPut(String apiBaseUrl, String endpoint, def body, Map<String, String> headers = [:]) {
		def baseObject = OR.findTestObject('Object Repository/API_Testing/PUT_Generic', [
			'endpoint': apiBaseUrl + endpoint,
			'body': body
		])
		if (headers && headers.size() > 0) {
			baseObject.setHttpHeaderProperties(buildHeaders(headers))
		}
		return WS.sendRequest(baseObject)
	}

	def static sendPatch(String apiBaseUrl, String endpoint, def body, Map<String, String> headers = [:]) {
		def baseObject = OR.findTestObject('Object Repository/API_Testing/PATCH_Generic', [
			'endpoint': apiBaseUrl + endpoint,
			'body': body
		])
		if (headers && headers.size() > 0) {
			baseObject.setHttpHeaderProperties(buildHeaders(headers))
		}
		return WS.sendRequest(baseObject)
	}

	def static sendDelete(String apiBaseUrl, String endpoint, Map<String, String> headers = [:]) {
		def baseObject = OR.findTestObject('Object Repository/API_Testing/DELETE_Generic', [
			'endpoint': apiBaseUrl + endpoint
		])
		if (headers && headers.size() > 0) {
			baseObject.setHttpHeaderProperties(buildHeaders(headers))
		}
		return WS.sendRequest(baseObject)
	}

	def static sendGraphQL(String baseUrl, String query, Map<String, Object> variables = [:], Map<String, String> headers = [:]) {
		def body = JsonOutput.toJson([
			query: query,
			variables: variables
		])
		def baseObject = OR.findTestObject('Object Repository/API_Testing/POST_Generic', [
			'endpoint': baseUrl,
			'body': body
		])
		if (headers && headers.size() > 0) {
			baseObject.setHttpHeaderProperties(buildHeaders(headers))
		}
		return WS.sendRequest(baseObject)
	}
}