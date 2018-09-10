package org.elsa.valord.job;

import lombok.extern.slf4j.Slf4j;
import org.elsa.valord.advice.Alimama;
import org.elsa.valord.advice.response.AuctionCode;
import org.elsa.valord.common.utils.Headers;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author valor
 * @date 2018/9/10 18:30
 */
@Slf4j
@Service
public class HandCookie {

    private Headers headers = Headers.getInstance();

    @Scheduled(cron = "0 0/2 * * * ?")
    public void hand() {
        if (null != headers.getTbCookie()) {
            AuctionCode.Codes codes = Alimama.getAuctionCode("562382995602");
            if (null != codes) {
                log.info("====== Scheduled job execute successfully. ======");
            }
        }
    }
}
