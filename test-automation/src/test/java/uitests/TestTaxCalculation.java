package uitests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import uipages.LoginPage;
import uipages.ProductPurchagePage;
import org.testng.annotations.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class TestTaxCalculation {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeClass
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterClass
    static void closeBrowser() {
        playwright.close();
    }
    LoginPage loginPage;
    ProductPurchagePage products;

    @BeforeMethod
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
        loginPage = new LoginPage(page);
        products = new ProductPurchagePage(page);

    }

    @AfterMethod
    void closeContext() {
        context.close();
    }

    String FIRST_NAME = "test";

    String LAST_NAME = "user";

    String POSTAL_CODE = "222222";

    public void checkOutItem(String userType, int item){
        /*
        This functions will performance check of the item. It will do for Sauce-lab-backpack but We can customise
        it for other items as well
         */
        loginPage.login(userType);
        assertThat(page.getByText("Swag Labs")).isVisible();
        if(item == 2){
        products.AddToCart("sauce-labs-backpack");
        products.AddToCart("sauce-labs-bike-light");
        products.clickOnCartIcon("2");
        }else{
            products.AddToCart("sauce-labs-backpack");
            products.clickOnCartIcon("1");
        }


        products.CheckOut();
        products.enterUserDetails(FIRST_NAME, LAST_NAME, POSTAL_CODE);

    }

    public void AssertTaxCalculation(){
        /*
        This function will perform the tax calculation based on item subtotal value. Tax is 8% of item subtotal
        If tax is not calculated correct then assertion will fail
         */
        products.clickContinueButton();
        double totalValue = products.parseText(products.getTextSummary());
        double tax = totalValue*0.08;
        double roundedNumber = Math.round(tax * 100.0) / 100.0;
        double actualTax = products.parseText(products.getTextTax());
        double totalAmount = products.parseText(products.totalValue());
        Assert.assertEquals(roundedNumber, actualTax, "Tax Calculation is incorrect, its not 8%");
        Assert.assertEquals(totalAmount, totalValue + actualTax);
        products.ClickonFinishButton();
        String confirmationMessage = products.getConfirmationMessage();
        Assert.assertEquals("Thank you for your order!", confirmationMessage, "Confirmation message is not correctly displayed");
    }


    /*
    Below Tests are created per user but we can parametrize these test as well based on particular user type
     */
    @Test
    public void validateTaxCalculationSingleItemStandardUser(){
        String userType = "standard";
        checkOutItem(userType, 1);
        AssertTaxCalculation();
    }

    @Test
    public void validateTaxCalculationMulitpleItemsStandardUser(){
        String userType = "standard";
        checkOutItem(userType, 2);
        AssertTaxCalculation();
    }

    @Test
    public void validateTaxCalculationPerformanceUser(){
        /*
        Validate We can purchage items using performance user. Performance glitch user taking little bit time to login
        We currently default timeout is 30s however if it will take more than that we can increase the timeout period.
         */
        String userType = "performance";
        checkOutItem(userType, 1);
        AssertTaxCalculation();
    }

    @Test
    public void validateTaxCalculationVisualUser(){

        String userType = "visual";
        checkOutItem(userType, 1);
        AssertTaxCalculation();
    }

    @Test
    public void validateTaxCalculationErrorUser(){
        /*
        Since Using Error User we can not complete the order as its giving error while clicking onn the finish button
        so if particular confirmation is not displayed in given time then test will fail.
         */
        String userType = "error";
        checkOutItem(userType, 1);
        AssertTaxCalculation();
    }


    @Test
    public void validateCheckoutProblemUser(){
        /*
        This test validate that use data filled is correctly persist or not since its a problem user
        data is not being persist correct so this test will fail.
         */
        String userType = "problem";
        checkOutItem(userType, 1);
        String firstName = products.GetAttributeValueFirstName();
        Assert.assertEquals(firstName, FIRST_NAME, "First Name field is not correctly filled");
        String lastName = products.GetAttributeValueLastName();
        Assert.assertEquals(lastName, LAST_NAME, "Last Name field is not filled");
        String postalCode = products.GetAttributeValuePostalCode();
        Assert.assertEquals(postalCode, POSTAL_CODE, "Postal code is not field");
    }

    @Test
    public void LoginTestError(){
        String userType = "locked";
        loginPage.login(userType);
        String logingError = loginPage.loginErrorMessage();
        Assert.assertEquals(logingError, "Epic sadface: Sorry, this user has been locked out.", "Login Error message is incorrect or misleading, Please check again");

    }

}
