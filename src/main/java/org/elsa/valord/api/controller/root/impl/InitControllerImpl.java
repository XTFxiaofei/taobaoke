package org.elsa.valord.api.controller.root.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elsa.valord.api.controller.root.AbsInitController;
import org.elsa.valord.common.utils.Chromes;
import org.elsa.valord.common.utils.Emails;
import org.elsa.valord.common.utils.Headers;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author valor
 * @date 2018/9/9 22:32
 */
@Slf4j
@RestController
public class InitControllerImpl extends AbsInitController {

    @Value("${selenium.tb.init}")
    private String init;

    @Value("${selenium.tb.flag}")
    private String flag;

    @Value("${selenium.tb.target}")
    private String target;

    @Value("${auth}")
    private String auth;

    private final String MODULE_STATIC = "login-box no-longlogin module-static";
    private final String MODULE_QUICK = "login-box no-longlogin module-quick";

    @Override
    public String init() {
        if (!StringUtils.equals(auth, request.getHeader("auth"))) {
            return "无操作权限";
        }

        try {
            Chromes.openTabAndFocus(init);
            Thread.sleep(1000L);

            // 判断登录模式 目标二维码登录
            WebElement loginBox = Chromes.findElementById("J_LoginBox");
            log.info("登录模式: " + loginBox.getAttribute("class"));
            if (MODULE_STATIC.equals(loginBox.getAttribute("class"))) {
                WebElement jStatic2quick = Chromes.findElementById("J_Static2Quick");
                Chromes.actions().click(jStatic2quick).perform();
            }

            // 获取二维码
            String qrXpath = "//*[@id=\"J_QRCodeImg\"]/img";
            Chromes.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(qrXpath)));
            WebElement qrCode = Chromes.findElementByXPath(qrXpath);
            String qrUrl = qrCode.getAttribute("src");
            log.info("二维码链接 => " + qrUrl);

            if (Chromes.scanUrl(new String[]{flag})) {
                Chromes.get(target);

                Thread.sleep(1000L);

                StringBuilder cookie = new StringBuilder();
                for (Cookie elem : Chromes.getCookies()) {
                    cookie.append(elem.getName()).append("=").append(elem.getValue()).append(";");
                }

                Headers headers = Headers.getInstance();
                boolean f = headers.putCookie(Headers.TB, cookie.toString());
                if (f) {
                    log.info("taobao cookie: " + headers.getTbCookie());
                    Chromes.close();
                    return "ok.";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Chromes.close();
        return "Error.";
    }
}
