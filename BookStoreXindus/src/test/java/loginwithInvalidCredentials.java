import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class loginwithInvalidCredentials {
    public WebDriver driver;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver" , "D:\\Getting started with Selenium\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bookbazaar.com/");

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test(priority = 1)
    public void signInPageUrlCheck(){
        WebElement signInEl = driver.findElement(By.xpath("//span[contains(text() , 'Sign In')]"));
        signInEl.click();

        String expectedUrl = "https://www.bookbazaar.com/Login";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl , expectedUrl , "Sing-in Page Url doesnot match");
    }

    @Test(priority = 2)
    public  void loginWithWrongPassword(){
        WebElement mobileNumEl = driver.findElement(By.id("LgnMobileOrEmail"));
        mobileNumEl.sendKeys("9494158504");
        WebElement passwordEl = driver.findElement(By.id("logintextpswd"));
        passwordEl.sendKeys("9494158508");
        WebElement signInPageEl = driver.findElement(By.xpath("//input[@class = \"button-input\"]"));
        signInPageEl.click();

        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text() = \"Please enter correct password.\"]")));

        WebElement errMsgEl = driver.findElement(By.xpath("//div[text() = \"Please enter correct password.\"]"));

        Assert.assertTrue(errMsgEl.isDisplayed() , "Login was not successful");
    }
}
