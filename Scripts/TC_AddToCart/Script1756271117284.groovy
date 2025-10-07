import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.model.FailureHandling.STOP_ON_FAILURE
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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import utils.JsonFileReader as JsonFileReader
import com.report.ExtentManager as ExtentManager
import utils.DependencyHandler as DependencyHandler

DependencyHandler.checkDependency('Sign_In', 'AddToCart')

try {
    KeywordUtil.logInfo('Signing into the account')

    WebUI.callTestCase(findTestCase('Test Cases/TC_SignIn'), [:])

    KeywordUtil.markPassed('Signed in to the account')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to sign into the account: ' + e.message)
} 

try {
    KeywordUtil.logInfo('Navigating to the category option page')

    CustomKeywords.'pages.HomePage.clickOnShopByCategoryOptions'(categoryOption, FailureHandling.STOP_ON_FAILURE)

    KeywordUtil.markPassed('Navigated to the category option page')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to category option page: ' + e.message)
} 

try {
    KeywordUtil.logInfo('Navigating to the product page')

    CustomKeywords.'pages.ComponentsPage.clickOnInstockProducts'(FailureHandling.STOP_ON_FAILURE)

    KeywordUtil.markPassed('Navigated to the product page')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to product page: ' + e.message)
} 

try {
    KeywordUtil.logInfo('Navigating to the checkout page')

    CustomKeywords.'pages.ProductPage.addingProductToCart'(FailureHandling.STOP_ON_FAILURE)

    KeywordUtil.markPassed('Navigated to the checkout page')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to checkout page: ' + e.message)
} 

try {
    KeywordUtil.logInfo('Clicking on checkout button')

    CustomKeywords.'pages.ShoppingCartPage.clickOnCheckout'(findTestObject('Object Repository/ShoppingCartPage/CheckOutButton'), 
        FailureHandling.STOP_ON_FAILURE)

    KeywordUtil.markPassed('Clicked on checkout button')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to click on checkout button: ' + e.message)
} 

try {
    KeywordUtil.logInfo('Entering billing details')

    List<Map> paymentFields = [[('id') : 'input-payment-firstname', ('jsonKey') : 'firstName', ('label') : 'First Name']
        , [('id') : 'input-payment-lastname', ('jsonKey') : 'lastName', ('label') : 'Last Name'], [('id') : 'input-payment-address-1'
            , ('jsonKey') : 'address', ('label') : 'Address'], [('id') : 'input-payment-city', ('jsonKey') : 'city', ('label') : 'City']
        , [('id') : 'input-payment-postcode', ('jsonKey') : 'postalCode', ('label') : 'Postal Code']]

    paymentFields.each({ 
            CustomKeywords.'pages.CheckOutPage.inputDetails'(findTestObject('Object Repository/CheckoutPage/dynamicInput', 
                    [('id') : it.id]), JsonFileReader.getJsonData(cartPage, it.jsonKey, GlobalVariable.jsonDataFilePath), it.label, FailureHandling.STOP_ON_FAILURE)
        })

    KeywordUtil.markPassed('Billing details entered')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to enter billing details: ' + e.message)
} 

try {
    KeywordUtil.logInfo('Navigating to the confirm order page')

    CustomKeywords.'pages.CheckOutPage.clickOnContinue'(FailureHandling.STOP_ON_FAILURE)

    KeywordUtil.markPassed('Navigated to the confirm order page')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to navigate to confirm order page: ' + e.message)
} 

try {
    KeywordUtil.logInfo('Confirming the order')

    CustomKeywords.'pages.ConfirmOrderPage.clickOnConfirmOrder'(findTestObject('Object Repository/ConfirmOrderPage/confirmOrder_Button'), 
        STOP_ON_FAILURE)

    KeywordUtil.markPassed('Order confirmed')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to confirm the order: ' + e.message)
} 
try {
    KeywordUtil.logInfo('Verifying the confirm order text')

    CustomKeywords.'pages.CheckOutSuccessPage.verifyOrderPlaced'(findTestObject('Object Repository/CheckOutSuccessPage/OrderConfirmed'), 
        JsonFileReader.getJsonData(orderPlacedPage, 'verifyText', GlobalVariable.jsonDataFilePath), FailureHandling.STOP_ON_FAILURE)

    KeywordUtil.markPassed('Verified the confirm order text')
}
catch (Exception e) {
    KeywordUtil.markFailed('Failed to verify confirm order text: ' + e.message)
}