package org.elsa.valord.self;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 * @author valor
 * @date 2018/9/3 16:18
 */
public class QRCodeDemo {
    public static void main(String[] args) {
        final String MODULE_STATIC = "login-box no-longlogin module-static";
        final String MODULE_QUICK = "login-box no-longlogin module-quick";
        final String url = "https://pub.alimama.com/common/code/getAuctionCode.json?auctionid=575345685215&adzoneid=129200277&siteid=208910108&scenes=1";
        System.setProperty("webdriver.chrome.driver", "/idea/taobaoke/ChromeDriver/chromedriver.exe");
        ChromeDriver browser = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(browser, 10);
        try {
            browser.get(url);
            Thread.sleep(1000L);
            // 切换到 iframe
            browser.switchTo().frame("taobaoLoginIfr");
            // 判断登录模式 目标二维码登录
            WebElement loginBox = browser.findElementById("J_LoginBox");
            System.out.println("登录模式: " + loginBox.getAttribute("class"));
            if (MODULE_STATIC.equals(loginBox.getAttribute("class"))) {
                WebElement J_Static2Quick = browser.findElementById("J_Static2Quick");
                new Actions(browser).click(J_Static2Quick).perform();
            }
            // 获取二维码
            String QRXpath = "//*[@id=\"J_QRCodeImg\"]/img";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(QRXpath)));
            WebElement QRCode = browser.findElementByXPath(QRXpath);
            System.out.println(QRCode.getAttribute("src"));
        } catch (Exception e) {
            browser.quit();
            e.printStackTrace();
        }
    }
}