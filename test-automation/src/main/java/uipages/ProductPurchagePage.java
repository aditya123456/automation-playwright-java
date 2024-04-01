package uipages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductPurchagePage {

    private final Locator addToCart;

    private final Locator checkOut;

    private final Locator firstName;

    private final Locator lastName;

    private final Locator postalCode;

    private final Locator continueButton;

    private final Page page;

    private final Locator summarySubLabel;

    private final Locator taxLabel;

    private final Locator finishButton;

    private final Locator confirmationMessage;

    private final Locator totalValue;


    public ProductPurchagePage(Page page) {
        this.page = page;
        this.addToCart = page.locator("[data-test=\"add-to-cart-sauce-labs-backpack\"]");
        this.checkOut = page.locator("[data-test=\"checkout\"]");
        this.firstName = page.locator("[data-test=\"firstName\"]");
        this.lastName = page.locator("[data-test=\"lastName\"]");
        this.postalCode = page.locator("[data-test=\"postalCode\"]");
        this.continueButton = page.locator("[data-test=\"continue\"]");
        this.summarySubLabel = page.locator("div.summary_subtotal_label");
        this.confirmationMessage = page.locator("h2.complete-header");
        this.taxLabel = page.locator("div.summary_tax_label");
        this.totalValue = page.locator("div.summary_info_label.summary_total_label");
        this.finishButton =  page.locator("[data-test=\"finish\"]");;
    }

    public void AddToCart(String itemName){
        Locator addToCartLocator = page.locator("[data-test=\"add-to-cart-"+itemName+"\"]");
        addToCartLocator.click();
    }

    public void CheckOut(){
        checkOut.click();
    }

    public void enterUserDetails(String first, String last, String postal){
        firstName.fill(first);
        lastName.fill(last);
        postalCode.fill(postal);
//        continueButton.click();

    }

    public void clickContinueButton(){
        continueButton.click();
    }

    public String GetAttributeValueLastName(){
        return lastName.getAttribute("value");
    }

    public String GetAttributeValueFirstName(){
        return firstName.getAttribute("value");
    }

    public String GetAttributeValuePostalCode(){
        return postalCode.getAttribute("value");
    }

    public void ClickonFinishButton(){
        finishButton.click();
    }

    public void clickOnCartIcon(String items){
        page.locator("a").filter(new Locator.FilterOptions().setHasText(items)).click();
    }

    public String getTextSummary(){
        return summarySubLabel.textContent();
    }

    public String totalValue(){
        return totalValue.textContent();
    }


    public String getConfirmationMessage(){
        try{
        return confirmationMessage.textContent();
        } catch (TimeoutError error){
            return "Timeout Error Element is not available or clickable";
        }

    }

    public String getTextTax(){
        return taxLabel.textContent();
    }

    public Double parseText(String inputString){
        /*
        This function parse the input string and return the decimal value. This is used to parse the subtotal item string
        and Tax string since it has amount.
         */
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(inputString);
        Double decimalValue = 0.0;

        if (matcher.find()) {
            String decimalString = matcher.group();
             decimalValue = Double.parseDouble(decimalString);
            System.out.println("Decimal value: " + decimalValue);
        } else {
            System.out.println("Decimal value not found in the input string.");
        }
        return decimalValue;
    }

}
