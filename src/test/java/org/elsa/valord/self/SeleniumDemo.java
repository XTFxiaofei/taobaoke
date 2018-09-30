package org.elsa.valord.self;

import org.elsa.valord.common.pojo.HttpResult;
import org.elsa.valord.common.utils.Headers;
import org.elsa.valord.common.utils.Https;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;

/**
 * @author valord577
 * @date 18-8-20 下午4:12
 */
public class SeleniumDemo {

    public static void main(String[] args) {

        final String url = "https://pub.alimama.com/common/code/getAuctionCode.json?auctionid=575345685215&adzoneid=129200277&siteid=208910108&scenes=1";

        System.setProperty("webdriver.chrome.driver", "/idea/taobaoke/ChromeDriver/chromedriver.exe");
        WebDriver browser = new ChromeDriver();

        browser.get(url);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(browser.getCurrentUrl());

        Scanner input = new Scanner(System.in);
        if ("y".equals(input.next())) {
            input.close();

            System.out.println(browser.getCurrentUrl());

            StringBuilder cookie = new StringBuilder();
            for (Cookie elem : browser.manage().getCookies()) {
                cookie.append(elem.getName()).append("=").append(elem.getValue()).append(";");
            }

            Headers headers = Headers.getInstance();
            boolean f = headers.putCookie(Headers.TB, cookie.toString());
            if (f) {
                System.out.println(headers.getHeader(Headers.TB).get("cookie"));

                try {
                    HttpResult result = Https.ofGet(url, headers.getHeader(Headers.TB));
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}