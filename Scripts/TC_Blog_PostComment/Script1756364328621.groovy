import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import utils.JsonFileReader as JsonFileReader
import com.report.ExtentManager as ExtentManager
try {
    KeywordUtil.logInfo('Navigating to the blog page')
    CustomKeywords.'pages.HomePage.clickOnBlog'(findTestObject('Object Repository/HomePage/Blogs'), 'Blog', FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('Navigated to the blog page')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to the blog page: ' + e.message)
}
try {
    KeywordUtil.logInfo('Navigating to the article page')
    CustomKeywords.'pages.BlogPage.clickOnArticle'(findTestObject('Object Repository/BlogsPage/Article'), FailureHandling.STOP_ON_FAILURE)
    KeywordUtil.markPassed('Navigated to the article page')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to the article page: ' + e.message)
}
try {
    KeywordUtil.logInfo('Entering the details to post a comment')
    List<Map<String, String>> articleFields = [
        [id: 'input-name', jsonKey: 'name', label: 'Name'],
        [id: 'input-email', jsonKey: 'email', label: 'Email']
    ]
    articleFields.each {
        CustomKeywords.'pages.ArticlePage.articleDetails'(
            findTestObject('Object Repository/ArticlePage/dynamicInput', [('id') : it.id]),
            JsonFileReader.getJsonData(section, it.jsonKey, GlobalVariable.jsonDataFilePath),
            it.label,
            FailureHandling.STOP_ON_FAILURE
        )
    }
    CustomKeywords.'pages.ArticlePage.inputCommentOnArticle'(
        findTestObject('Object Repository/ArticlePage/inputComment'),
        JsonFileReader.getJsonData(section, 'comment', filepath),
        FailureHandling.STOP_ON_FAILURE
    )
    CustomKeywords.'pages.ArticlePage.clickOnPostCommentButton'(
        findTestObject('Object Repository/ArticlePage/postCommentButton'),
        FailureHandling.STOP_ON_FAILURE
    )
    KeywordUtil.markPassed('Details for posting a comment have been entered')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to enter comment details: ' + e.message)
}
try {	
    KeywordUtil.logInfo('Verifying Success Message')
    CustomKeywords.'pages.ArticlePage.verifySuccessText'(
        findTestObject('Object Repository/ArticlePage/successMessage'),
        JsonFileReader.getJsonData(section, 'successMessage', GlobalVariable.jsonDataFilePath),	
        FailureHandling.STOP_ON_FAILURE
    )
    KeywordUtil.markPassed('Verified Success Message')
} catch (Exception e) {
    KeywordUtil.markFailed('Failed to verify success message: ' + e.message)
}