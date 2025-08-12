//Commit by Ganesh
package com.yatra.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class yatraAutomation {
    public static void main(String[] args) throws InterruptedException {
        //intialise the driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);
        //launch the browser
        driver.get("https://www.yatra.com/");
        //maximise the window
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        //click on departure date
        By webNotificationContainer=By.xpath("//div[@id='webengage-notification-container']");
        By popUpLocator=By.xpath("//img[contains(@class,'bee-popup-center')]");
        By popUpCloseBtn=By.xpath("//button[@name=\"close\"]");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label='Departure Date inputbox']"))).click();

//       Locatora
        By julMonthContainer = By.xpath("//div[@class=\"react-datepicker__month-container\"]");
        List<WebElement> CalenderMonths = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(julMonthContainer));
        System.out.println(CalenderMonths.size());
        WebElement CurrentMonthCalender = CalenderMonths.get(0);
        Thread.sleep(3000);
        By date_Locator = By.xpath(".//div[contains(@class,'react-datepicker__day')]");
        By priceslocator = By.xpath(".//span[contains(@class,'custom-day-content')]");
        List<WebElement> pricesList = CurrentMonthCalender.findElements(priceslocator);
        int LowestPriceInCurentMonth=Integer.MAX_VALUE;
        WebElement dateEle=null;
        for(WebElement price:pricesList){
            System.out.println(price.getText());
            if(price.getText().length()>0){
                String priceStr=price.getText().replaceAll("₹","").replace(",","");
                System.out.println(priceStr);
                int priceInt=Integer.parseInt(priceStr);
                if(priceInt<LowestPriceInCurentMonth){
                    LowestPriceInCurentMonth=priceInt;
                    dateEle=price;
                }
            }
        }
        String date=dateEle.findElement(By.xpath(".//../..")).getAttribute("aria-label");
        WebElement NextMonthCalender = CalenderMonths.get(1);
        Thread.sleep(3000);
         pricesList = NextMonthCalender.findElements(priceslocator);
        int LowestPriceInNextMonth=Integer.MAX_VALUE;
        WebElement dateEl=null;
        for(WebElement price:pricesList){
            if(price.getText().length()>0){
                String priceStr=price.getText().replaceAll("₹","").replace(",","");
                System.out.println(priceStr);
                int priceInt=Integer.parseInt(priceStr);
                if(priceInt<LowestPriceInNextMonth){
                    LowestPriceInNextMonth=priceInt;
                    dateEl=price;
                }
            }
        }
        String dateInNext_month=dateEl.findElement(By.xpath(".//../..")).getAttribute("aria-label");

        if (LowestPriceInCurentMonth < LowestPriceInNextMonth) {
            System.out.println("👉 Current month has the lowest price: " + LowestPriceInCurentMonth+"  ANd the Date is "+date);
        } else if (LowestPriceInNextMonth < LowestPriceInCurentMonth) {
            System.out.println("👉 Next month has the lowest price: " + LowestPriceInNextMonth+"  ANd the Date is "+date);
        } else {
            System.out.println("👉 Both months have the same lowest price: " + LowestPriceInCurentMonth+"  And the Dats are "+date+" or "+dateInNext_month);
        }
        driver.quit();
    }
}
