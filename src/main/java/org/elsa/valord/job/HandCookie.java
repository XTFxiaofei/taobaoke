package org.elsa.valord.job;

import lombok.extern.slf4j.Slf4j;
import org.elsa.valord.advice.Alimama;
import org.elsa.valord.advice.response.AuctionCode;
import org.elsa.valord.common.utils.Chromes;
import org.elsa.valord.common.utils.Headers;
import org.openqa.selenium.Cookie;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * @Author:fly
 * @Description: 定时刷新cookie
 * @Date:1:33 2018/10/1
 */
@Slf4j
@Service
public class HandCookie {

    private Headers headers = Headers.getInstance();

    private static StringBuilder cookie = new StringBuilder();

    private static final String MY_UNION = "https://pub.alimama.com/myunion.htm?spm=a219t.7900221/1.1998910419.db9f5f632.2a8f75a5EBZv2M#!/report/detail/taoke";

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

//    @Scheduled(cron = "0 0/10 * * * ?")
//    public void hand1() {
//        if (null != headers.getTbCookie()) {
//            AuctionCode.Codes codes = Alimama.getAuctionCode("562382995602");
//            if (null != codes) {
//                log.info("====== Scheduled job execute successfully. ======");
//            }
//        }
//    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void hand2() {
        if (null != headers.getTbCookie()) {
            Chromes.openTabAndFocus(MY_UNION);

            try {
                Thread.sleep(1000L);

                if (Chromes.scanUrl(new String[]{MY_UNION})) {

                    log.info("StringBuilder => " + cookie);
                    if (cookie.length() > 0) {
                        cookie.delete(0, cookie.length());
                    }
                    for (Cookie elem : Chromes.getCookies()) {
                        cookie.append(elem.getName()).append("=").append(elem.getValue()).append(";");
                    }

                    boolean f = headers.putCookie(Headers.TB, cookie.toString());
                    if (f) {
                        log.info("taobao cookie: " + headers.getTbCookie());
                        log.info("====== Scheduled change Cookies successfully. ======");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Chromes.close();
        }
    }
}
