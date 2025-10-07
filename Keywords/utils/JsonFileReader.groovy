package utils
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import utils.Helper as helper
import groovy.json.JsonSlurper
import groovy.json.JsonSlurper as JsonSlupper
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
public class JsonFileReader {
	static def getJsonData(String section, String key, String filepath) {
		if (!filepath) {
			KeywordUtil.markWarning("File path is null or empty.")
			return null
		}
		def jsonFile = new File(filepath)
		if (!jsonFile.exists()) {
			KeywordUtil.markFailed("File not found at path: ${filepath}")
			return null
		}
		def jsondata = new JsonSlurper().parse(jsonFile)
		if (jsondata instanceof Map) {
			def sectionData = jsondata[section]
			if (sectionData instanceof Map) {
				return sectionData[key]
			} else {
				KeywordUtil.markWarning("Section '${section}' is not a valid object.")
			}
		} else {
			KeywordUtil.markWarning("JSON root is not a Map.")
		}
		return null
	}
	static def updateJsonValue(String section, String key, def newValue, String filepath) {
		if (!filepath) {
			KeywordUtil.markWarning("File path is null or empty.")
			return null
		}
		def jsonFile = new File(filepath)
		if (!jsonFile.exists()) {
			KeywordUtil.markFailed("File not found at path: ${filepath}")
			return null
		}
		def jsondata = new JsonSlurper().parse(jsonFile)
		def updated = false
		if (jsondata instanceof Map) {
			def sectionData = jsondata[section]
			if (sectionData instanceof Map) {
				sectionData[key] = newValue
				KeywordUtil.logInfo("Updated '${key}' in '${section}' section to '${newValue}'.")
				updated = true
			} else {
				KeywordUtil.markWarning("Section '${section}' is not a valid object.")
			}
		} else {
			KeywordUtil.markWarning("JSON root is not a Map.")
		}
		if (updated) {
			saveJsonData(jsondata, jsonFile)
		}
	}
	static def getSectionData(String section, String filepath) {
		if (!filepath) {
			KeywordUtil.markWarning("File path is null or empty.")
			return null
		}
		def jsonFile = new File(filepath)
		if (!jsonFile.exists()) {
			KeywordUtil.markFailed("File not found at path: ${filepath}")
			return null
		}
		def jsondata = new JsonSlurper().parse(jsonFile)
		if (jsondata instanceof Map) {
			def sectionData = jsondata[section]
			if (sectionData instanceof Map) {
				return sectionData
			} else {
				KeywordUtil.markWarning("Section '${section}' is not a valid object.")
			}
		} else {
			KeywordUtil.markWarning("JSON root is not a Map.")
		}
		return null
	}
	static void storeJsonData(String filePath, String section, String key, def value) {
		File file = new File(filePath)
		def jsonSlurper = new JsonSlurper()
		def jsonData = [:]
		if (file.exists()) {
			try {
				jsonData = jsonSlurper.parse(file)
			} catch (Exception e) {
				KeywordUtil.markWarning("Failed to parse JSON file: ${e.message}")
				jsonData = [:]
			}
		}
		if (!(jsonData[section] instanceof Map)) {
			jsonData[section] = [:]
		}
		jsonData[section][key] = value
		saveJsonData(jsonData, file)
		KeywordUtil.logInfo("Appended '${key}: ${value}' to section '${section}' in file '${filePath}'.")
	}
	static void storeApiResponse(String sectionName, def responseData, String filePath) {
		def jsonSlurper = new JsonSlurper()
		def file = new File(filePath)
		def parsedResponse = jsonSlurper.parseText(responseData.getResponseBodyContent())
		def json = file.exists() ? jsonSlurper.parse(file) : [:]
		json[sectionName] = parsedResponse
		saveJsonData(json, file)
		KeywordUtil.logInfo("Stored API response under section '${sectionName}' in file '${filePath}'.")
	}
	static def getSectionData(def parsedJson, String section) {
		for (def item : parsedJson) {
			if (item.containsKey(section)) {
				return item[section]
			}
		}
		KeywordUtil.markFailed("Section '${section}' not found in response.")
		return null
	}
	static def saveJsonData(def data, File file) {
		file.text = JsonOutput.prettyPrint(JsonOutput.toJson(data))
		KeywordUtil.logInfo("Saved updated JSON data to file: ${file.path}")
	}
}