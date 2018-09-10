package org.elsa.valord.self;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author valor
 * @date 2018/9/9 14:09
 */
public class Tab {

    public static void main(String[] args) throws Exception {


        System.setProperty("webdriver.chrome.driver", "/Users/valor/Vicky/ChromeDriver/chromedriver");
        ChromeDriver browser = new ChromeDriver();
        System.out.println(browser.getCurrentUrl());
        browser.get("https://www.taobao.com/");

        Thread.sleep(2000L);

        String js = "window.open('https://www.bejson.com/');" +
                "window.open('about:blank', '_blank');" +
                "window.open('https://baidu.com');" +
                "window.open('https://google.com');" +
                "window.open('http://tool.chaozhi.hk/#/tklParse');";
        browser.executeScript(js);

        Thread.sleep(5000L);
        System.out.println(browser.getWindowHandles());
        for (String a : browser.getWindowHandles()) {
            browser.switchTo().window(a);
            System.out.println(a + " -> " + browser.getTitle());
            System.out.println(browser.getCurrentUrl());
        }

        Object[] o = browser.getWindowHandles().toArray();
        for (Object o1 : o) {
            System.out.println(o1.toString());
        }
    }
}
