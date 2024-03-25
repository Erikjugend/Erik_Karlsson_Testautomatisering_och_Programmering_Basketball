package stepDefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.UUID;
import static org.junit.Assert.assertEquals;

public class BasketStepdefs {
    private WebDriver driver;

    //Run multiple browsers depending on what's specified in the feature file
    @Given("I am using {string} as browser")
    public void iAmUsingAsBrowser(String browser) {
        if(browser.equals("edge")) {
            driver = new EdgeDriver();
        }else if(browser.equals("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver();
        }
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @And("I have navigated to the account-creation page on basketballengland")
    public void iHaveNavigatedToTheAccountCreationPageOnBasketballengland() {
    }

    //Types the date of birth into the correct field
    @When("I enter my {string}")
    public void iEnterMy(String DoB) {
        WebElement birth = driver.findElement(By.id("dp"));
        birth.sendKeys(DoB);
    }

    @And("I enter my {string} and {string}")
    public void iEnterMyAnd(String firstName, String lastName) {
        //Types first name into field
        WebElement nameOne = driver.findElement(By.id("member_firstname"));
        nameOne.sendKeys(firstName);
        //Types last name into field
        WebElement nameTwo = driver.findElement(By.id("member_lastname"));
        nameTwo.sendKeys(lastName);
    }

    @And("I enter my email address in both fields")
    public void iEnterMyEmailAddressInBothFields() {
        //Calls on the random email method
        String randomEmail = generateRandomEmail();
        //Enters the generated email into the field
        WebElement emailField = driver.findElement(By.id("member_emailaddress"));
        emailField.sendKeys(randomEmail);
        //Enters generated email into second field
        WebElement confirmEmail = driver.findElement(By.id("member_confirmemailaddress"));
        confirmEmail.sendKeys(randomEmail);
    }
    //Generates a random 8 character string to insert to make a random email adress
    private String generateRandomEmail() {
        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        return "eriktest" + randomString + "@mail.com";
    }

    //Types password into the field
    @And("I choose a {string}")
    public void iChooseA(String psw) {
        WebElement passwordOne = driver.findElement(By.id("signupunlicenced_password"));
        passwordOne.sendKeys(psw);
    }

    //Types password into the second field
    @And("I repeat the {string}")
    public void iRepeatThe(String psw2) {
        WebElement passwordTwo = driver.findElement(By.id("signupunlicenced_confirmpassword"));
        passwordTwo.sendKeys(psw2);
    }

    @And("I have ticked the {string}")
    public void iHaveTickedThe(String termsAgreed) {
        //Clicks the required check boxes
        click(driver,By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div.md-checkbox.margin-top-10 > label > span.box"));
        click(driver,By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(7) > label > span.box"));
        WebElement termsBox = driver.findElement(By.cssSelector("#signup_form > div:nth-child(12) > div > div:nth-child(2) > div:nth-child(1) > label > span.box"));
        //If-statement that if the example in the feature file for this box is marked as "true", check the box
        if(termsAgreed.equalsIgnoreCase("true")) {
            if(!termsBox.isSelected()) {
                termsBox.click();
            }
        } else if(termsBox.isSelected()) {
            termsBox.click();
        }
    }

    //Clicks the confirm button to join the site as a member
    @And("I press the confirm button")
    public void iPressTheConfirmButton() {
        WebElement joinButton = driver.findElement(By.cssSelector("#signup_form > div.form-actions.noborder > input"));
        joinButton.click();
    }

    //Method to verify that an account has been successfully created
    @Then("I get the status success")
    public void iCreateMyAccountSuccessfully() {
        WebElement accountCreated = driver.findElement(By.xpath("/html/body/div/div[2]/div/h2"));
        String actual = accountCreated.getText();
        String expected = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
        assertEquals(expected,actual);
        driver.quit();
    }

    //Method to verify that an error message is displayed if the user doesn't enter a last name
    @Then("I get the status no last name")
    public void verifyErrorMessageNoLastName() {
        WebElement noLastName = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[5]/div[2]/div/span/span"));
        String actual = noLastName.getText();
        String expected = "Last Name is required";
        assertEquals(expected,actual);
        System.out.println(actual);
        driver.quit();
    }

    //Method to verify that an error message is displayed if the password
    // in the second field doesn't match the one in the first one
    @Then("I get the status no identical password")
    public void verifyErrorMessageNotSamePsw() {
        WebElement notSamePsw = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[8]/div/div[2]/div[2]/div/span/span"));
        String actual = notSamePsw.getText();
        String expected = "Password did not match";
        assertEquals(expected,actual);
        System.out.println(actual);
        driver.quit();
    }

    //Method to verify that an error message is displayed if the user doesn't check the terms and conditions box
    @Then("I get the status no terms and conditions")
    public void verifyErrorMessageNoTermsChecked() {
        WebElement noTerms = driver.findElement(By.xpath("//*[@id=\"signup_form\"]/div[11]/div/div[2]/div[1]/span/span"));
        String actual = noTerms.getText();
        String expected = "You must confirm that you have read and accepted our Terms and Conditions";
        assertEquals(expected,actual);
        System.out.println(actual);
        driver.quit();
    }

    //Creates an explicit wait method until something can be clickable that's usable by any method by calling for it
    private static void click(WebDriver driver, By by) {
        (new WebDriverWait(driver,Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }
}
