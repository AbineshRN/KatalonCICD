package utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.configuration.RunConfiguration
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

class loadProfileVariables {
	static Map<String, Object> loadAllProfileVariables(String profileName) {
		try {
			String projectDir = RunConfiguration.getProjectDir()
			File profileFile = new File("${projectDir}/Profiles/${profileName}.glbl")

			if (!profileFile.exists()) {
				throw new Exception("Profile '${profileName}' not found at ${profileFile}")
			}

			def profileXml = new XmlSlurper().parse(profileFile)
			def variablesMap = [:]

			profileXml.GlobalVariableEntity.each { variable ->
				String name = variable.name.text()
				String value = variable.initValue.text().replaceAll(/^'|'$/, "")
				variablesMap[name] = value
			}

			return variablesMap
		} catch (Exception e) {
			KeywordUtil.logInfo("Failed to load profile '${profileName}': ${e.message}")
			return [:]
		}
	}
}