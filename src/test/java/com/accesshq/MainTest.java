package com.accesshq;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainTest {
    private final String BASE_URL = "https://d3ovkzfkbrwp1z.cloudfront.net/";
    private WebDriver driver;

    @BeforeEach
    public void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\MicroRentals\\pphal\\resource\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void invalidEmailTest(){
        // Enter email and phone
        enterEmailAndPhone();

        //Submit form
        submitContactForm();

        // wait till error message popup
        WebDriverWait wait = new WebDriverWait(driver,10);
        String invalidEmailMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#email-err"))).getText();

        //Assert
        String expectedInvalidEmailMessage = "Email is invalid";
        Assertions.assertEquals(expectedInvalidEmailMessage,invalidEmailMessage);
    }

    @Test
    public void invalidPhoneNumberTest(){
        // Enter email and phone
        enterEmailAndPhone();

        //Submit form
        submitContactForm();

        // Get Invalid Phone number error message
        WebDriverWait wait = new WebDriverWait(driver,10);
        String invalidPhoneNumMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#telephone-err"))).getText();

        //Assert
        String expectedInvalidPhoneNumMessage = "Telephone is invalid";
        Assertions.assertEquals(expectedInvalidPhoneNumMessage,invalidPhoneNumMessage);
    }

    @Test
    public void clearEmailErrorMessageTest(){
        // Enter email and phone
        enterEmailAndPhone();

        // Submit contact form
        submitContactForm();

        // wait till email error message popup
        WebDriverWait wait = new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#email-err")));

        // Clear contact form
        clearContactForm();

        // Get invalid email error message
        String emailErrorMessage = "...";
        try{
            emailErrorMessage = driver.findElement(By.cssSelector("#email-err")).getText();
        }catch (NotFoundException e){
            emailErrorMessage = "";
        }

        //Assert
        Assertions.assertEquals("",emailErrorMessage);
    }

    @Test
    public void clearTelephoneErrorMessageTest(){
        // Enter email and phone
        enterEmailAndPhone();

        // Submit contact form
        submitContactForm();

        // wait till telephone error message popup
        WebDriverWait wait = new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#telephone-err")));

        // Clear contact form
        clearContactForm();

        // Get invalid email error message
        String telephoneErrorMessage = "...";
        try{
            telephoneErrorMessage = driver.findElement(By.cssSelector("#telephone-err")).getText();
        }catch (NotFoundException e){
            telephoneErrorMessage = "";
        }

        //Assert
        Assertions.assertEquals("",telephoneErrorMessage);
    }

    @Test
    public void confirmVeganPizzaPriceTest(){
        //Open home page
        driver.get(BASE_URL);

        //Click to menu tab
        driver.findElement(By.cssSelector("[aria-label = menu]")).click();

        //Get all pizzas cards
        Boolean allVeganPizzaIs14dot99 = true;
        String pizzaName = "";
        String pizzaPriceAsString = "";
        int count = 0;
        List<WebElement> pizzaCards = driver.findElements(By.className("menuItem"));

        for(WebElement pizza : pizzaCards){
            //get pizza name
            pizzaName = pizza.findElement(By.cssSelector("span.name")).getText();

            //check pizza price
            if(pizzaName.contains("Vegan")){
                pizzaPriceAsString = pizza.findElement(By.className("price")).getText();

                //Assert
                Assertions.assertEquals("$14.99",pizzaPriceAsString);
                System.out.println("Test number : " + ++count);
            }
        }
    }


    private void enterEmailAndPhone(){
        //Open home page
        driver.get(BASE_URL);

        //Click to contact page
        driver.findElement(By.cssSelector("[aria-label = contact]")).click();

        //Enter email
        driver.findElement(By.cssSelector("[name = email")).sendKeys("xxx");

        //Enter telephone
        driver.findElement(By.cssSelector("[name = telephone")).sendKeys("xxx");
    }

    private void submitContactForm(){
        //Click submit button
        driver.findElement(By.cssSelector("[aria-label = submit")).click();
    }

    private void clearContactForm(){
        //Click clear button
        driver.findElement(By.cssSelector("[aria-label = clear")).click();
    }


//    @AfterEach
//    public void  cleanup(){
//        driver.quit();
//    }
}
