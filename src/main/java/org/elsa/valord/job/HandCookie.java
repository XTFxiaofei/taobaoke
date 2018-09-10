package org.elsa.valord.job;

import lombok.extern.slf4j.Slf4j;
import org.elsa.valord.advice.Alimama;
import org.elsa.valord.advice.response.AuctionCode;
import org.elsa.valord.common.pojo.HttpResult;
import org.elsa.valord.common.utils.Dates;
import org.elsa.valord.common.utils.Headers;
import org.elsa.valord.common.utils.Https;
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

    @Scheduled(cron = "30 0/5 * * * ?")
    public void hand2() {
        if (null != headers.getTbCookie()) {
            String url = "https://pub.alimama.com/report/getTbkPaymentDetails.json?" +
                    "startTime=" + df.format(Dates.addDay(new Date(), -1)) +
                    "&endTime=" + df.format(new Date()) +
                    "&payStatus=&queryType=1&toPage=1&perPageSize=20";
            log.info("==== Scheduled URL: " + url + " ====");

            try {
                HttpResult result = Https.ofGet(url, headers.getHeader(Headers.TB));
                if (200 == result.getCode() && null != result.getContent()) {
                    log.info("httpResult => " + result);
                    log.info("====== Scheduled job execute successfully. ======");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
