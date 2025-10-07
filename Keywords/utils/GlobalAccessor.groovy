package utils

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.KeywordUtil

import groovy.util.ConfigSlurper
import groovy.util.ConfigObject

class GlobalAccessor {

	static Object getProfileVariable(String profileName, String variableName) {
		try {
			String projectDir = RunConfiguration.getProjectDir()
			File profileFile = new File("${projectDir}/Profiles/${profileName}.glbl")

			if (!profileFile.exists()) {
				throw new Exception("Profile '${profileName}' not found at ${profileFile}")
			}

			def profileXml = new XmlSlurper().parse(profileFile)

			def variable = profileXml.GlobalVariableEntity.find { it.name.text() == variableName }

			if (variable) {
				return variable.initValue.text().replaceAll(/^'|'$/, "")
			} else {
				throw new Exception("Variable '${variableName}' not found in profile '${profileName}'")
			}
		} catch (Exception e) {
			KeywordUtil.markFailed("Failed to fetch the variable from the specified profile")
			return null
		}
	}
}