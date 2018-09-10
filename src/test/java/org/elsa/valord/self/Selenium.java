package org.elsa.valord.self;

import com.alibaba.fastjson.JSON;
import org.elsa.valord.advice.response.AuctionCode;
import org.elsa.valord.common.pojo.HttpResult;
import org.elsa.valord.common.utils.Headers;
import org.elsa.valord.common.utils.Https;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author valord577
 * @date 18-8-20 下午4:12
 */
public class Selenium {

    public static void main(String[] args) {

        final String url0 = "https://login.taobao.com/member/login.jhtml?style=mini&newMini2=true&css_style=alimama&from=alimama&redirectURL=http%3A%2F%2Fwww.alimama.com&full_redirect=true&disableQuickLogin=true";
        final String url1 = "https://pub.alimama.com/common/code/getAuctionCode.json?auctionid=562382995602&adzoneid=11639900145&siteid=62250400&scenes=1";

//        System.setProperty("webdriver.chrome.driver", "/home/elsa/Vicky/Google/chromedriver/chromedriver_2.40_v66_v68");
        System.setProperty("webdriver.chrome.driver", "/Users/valor/Vicky/ChromeDriver/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless");

        ChromeDriver browser = new ChromeDriver();

        try {
            browser.get(url0);

            Thread.sleep(1000L);

            System.out.println(browser.getCurrentUrl());

            for (int i = 0; i < 31; i++) {
                System.out.println(i + ":" + browser.getCurrentUrl());

                if (30 == i) {
                    browser.quit();
                    return;
                }

                if (browser.getCurrentUrl().startsWith("https://www.alimama.com/")) {
                    browser.get(url1);
                    Thread.sleep(2000L);
                    break;
                }

                Thread.sleep(2000L);
            }

            System.out.println(browser.getCurrentUrl());

            StringBuilder cookie = new StringBuilder();
            for (Cookie elem : browser.manage().getCookies()) {
                cookie.append(elem.getName()).append("=").append(elem.getValue()).append(";");
            }

            Headers headers = Headers.getInstance();
            boolean f = headers.putCookie(Headers.TB, cookie.toString());
            if (f) {
                System.out.println(headers.getHeader(Headers.TB).get(Headers.COOKIE));

                try {
                    HttpResult result = Https.ofGet(url1, headers.getHeader(Headers.TB));
                    System.out.println(result);
                    AuctionCode auctionCode = JSON.parseObject(result.getContent(), AuctionCode.class);
                    System.out.println(auctionCode.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e) {
            browser.quit();
            e.printStackTrace();
        }
    }
}
