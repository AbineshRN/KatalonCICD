package utils

import org.everit.json.schema.loader.SchemaLoader
import org.everit.json.schema.Schema
import org.everit.json.schema.ValidationException
import org.json.JSONObject
import org.json.JSONTokener
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.annotation.Keyword

class SchemaValidator {

	@Keyword
	static def validateJsonWithSchema(String responseJson, String schemaJson) {
		try {
			JSONObject responseObj = new JSONObject(new JSONTokener(responseJson))
			JSONObject schemaObj = new JSONObject(new JSONTokener(schemaJson))

			def properties = schemaObj.optJSONObject("properties")
//			Returns the nested object if it exists
//			Returns null if it doesn't (without throwing an exception)
			if (properties != null) {
				KeywordUtil.logInfo("Schema expects the following fields:")
				properties.keySet().each { key ->
					def field = properties.getJSONObject(key)
					def type = field.optString("type", "unknown")
					KeywordUtil.logInfo("->Field: '${key}' | Type: '${type}'")
				}
			}

			Schema schema = SchemaLoader.load(schemaObj)
			schema.validate(responseObj)

			KeywordUtil.logInfo("Schema validation passed.")
		} catch (ValidationException e) {
			KeywordUtil.markFailed("Schema validation failed.")
			e.getCausingExceptions().each { cause ->
				KeywordUtil.markFailed("Field: ${cause.pointerToViolation} | Error: ${cause.message}")
			}
			if (e.getCausingExceptions().isEmpty()) {
				KeywordUtil.markFailed("Field: ${e.pointerToViolation} | Error: ${e.message}")
			}
		} catch (Exception e) {
			KeywordUtil.markFailed("Unexpected error during schema validation: ${e.message}")
		}
	}
}