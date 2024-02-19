import org.openqa.selenium.Alert;
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
import java.util.List;


public class BookStore {
    public WebDriver driver;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver" , "D:\\Getting started with Selenium\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bookbazaar.com/");
        WebElement signInEl = driver.findElement(By.xpath("//span[contains(text() , 'Sign In')]"));
        signInEl.click();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    @Test(priority = 1)
    public void signInPageUrlCheck(){
        String expectedUrl = "https://www.bookbazaar.com/Login";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl , expectedUrl , "Sing-in Page Url doesnot match");
    }

    @Test(priority = 2)
    public  void loginWithValidCredentials(){
        WebElement mobileNumEl = driver.findElement(By.xpath("//div[@class = 'bmargin15']/input[@id = 'LgnMobileOrEmail']"));
        mobileNumEl.sendKeys("9494158504");
        WebElement passwordEl = driver.findElement(By.id("logintextpswd"));
        passwordEl.sendKeys("9494158504");
        WebElement signInPageEl = driver.findElement(By.xpath("//input[@class = \"button-input\"]"));
        signInPageEl.click();

        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id = \"ctl00_loginname\"]")));

        WebElement profileNameEl = driver.findElement(By.xpath("//span[@id = \"ctl00_loginname\"]"));

        Assert.assertTrue(profileNameEl.isDisplayed() , "Login was not successful");
    }

    @Test(priority = 3)
    public void searchFunctionality(){
        WebElement searchInputEl = driver.findElement(By.xpath("//input[@id = 'small-searchterms']"));
        searchInputEl.sendKeys("Sparsh Class 10");
        WebElement searchIconEl = driver.findElement(By.xpath("//div[@title = 'Search']"));
        searchIconEl.click();

        List<WebElement> searchedItemsList = driver.findElements(By.xpath("//li[@class = 'item last']"));
        int searchedItemsCount = searchedItemsList.size();
        Assert.assertEquals(searchedItemsCount , 5 , "Search Count doesnot matches");
    }

    @Test(priority = 4)
    public void addToCart(){
        WebElement searchInputEl = driver.findElement(By.xpath("//input[@id = 'small-searchterms']"));
        searchInputEl.sendKeys("Sparsh Class 10");
        WebElement searchIconEl = driver.findElement(By.xpath("//div[@title = 'Search']"));
        searchIconEl.click();

        WebElement secondItemClick = driver.findElement(By.xpath("//h2[@class = 'product-name name']/a[contains(text() , 'Sparsh Class 10')][2]"));
        secondItemClick.click();
        WebElement addToCartBtnEl = driver.findElement(By.xpath("//span[text() = 'Add to Cart']"));
        addToCartBtnEl.click();

        WebElement countCartEl = driver.findElement(By.id("lnkcartdesk"));
        countCartEl.click();
        WebElement countInCart = driver.findElement(By.xpath("//div[@class = 'bset_dlt']/div[@class = 'cart_t_itm'][1]/span"));
        String countTxt = countInCart.getText();
        Assert.assertEquals(countTxt , 1 , "cart count doesnot matches");

        WebElement deleteCartItem = driver.findElement(By.xpath("//div[@class = 'sp-quantity']/div//a[contains(text() , 'Remove Item')]"));
        deleteCartItem.click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        String displayedText = alert.getText();
        Assert.assertEquals(displayedText , "Product Deleted successfully" , "Item doesnot deleted successfully");
        alert.accept();


    }
}
