package org.elsa.valord.job;

import lombok.extern.slf4j.Slf4j;
import org.elsa.valord.advice.Alimama;
import org.elsa.valord.advice.response.AuctionCode;
import org.elsa.valord.common.pojo.HttpResult;
import org.elsa.valord.common.utils.Chromes;
import org.elsa.valord.common.utils.Dates;
import org.elsa.valord.common.utils.Headers;
import org.elsa.valord.common.utils.Https;
import org.openqa.selenium.Cookie;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author valor
 * @date 2018/9/10 18:30
 */
@Slf4j
@Service
public class HandCookie {

    private Headers headers = Headers.getInstance();

    private static StringBuilder cookie = new StringBuilder();

    private static final String MY_UNION = "https://pub.alimama.com/myunion.htm";

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Scheduled(cron = "0 0/2 * * * ?")
    public void hand1() {
        if (null != headers.getTbCookie()) {
            AuctionCode.Codes codes = Alimama.getAuctionCode("562382995602");
            if (null != codes) {
                log.info("====== Scheduled job execute successfully. ======");
            }
        }
    }

    @Scheduled(cron = "33 0/4 * * * ?")
    public void hand2() {
        if (null != headers.getTbCookie()) {
            Chromes.openTabAndFocus(MY_UNION);
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

            Chromes.close();
        }
    }
}
