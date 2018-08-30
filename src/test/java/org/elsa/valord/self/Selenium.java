package org.elsa.valord.self;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;

/**
 * @author valord577
 * @date 18-8-20 下午4:12
 */
public class Selenium {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "/home/elsa/Vicky/Google/chromedriver/chromedriver_2.40_v66_v68");
        WebDriver web = new ChromeDriver();

        web.get("https://pub.alimama.com/common/code/getAuctionCode.json?auctionid=562382995602&adzoneid=11639900145&siteid=62250400&scenes=1");

        Scanner input = new Scanner(System.in);
        if ("y".equals(input.next())) {
            input.close();

        }
    }
}
