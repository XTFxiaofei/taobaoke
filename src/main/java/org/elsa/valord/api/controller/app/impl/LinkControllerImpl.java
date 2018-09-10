package org.elsa.valord.api.controller.app.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.MultiMap;
import org.elsa.valord.advice.Alimama;
import org.elsa.valord.advice.response.AuctionCode;
import org.elsa.valord.api.controller.app.AbsLinkController;
import org.elsa.valord.api.response.GeneralResult;
import org.elsa.valord.common.utils.Chromes;
import org.elsa.valord.common.utils.Patterns;
import org.elsa.valord.common.utils.URLs;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author valor
 * @date 2018/9/6 12:46
 */
@Slf4j
@RestController
public class LinkControllerImpl extends AbsLinkController {

    private final String tbDomain = "item.taobao";
    private final String tmDomain = "detail.tmall";

    @Override
    public DeferredResult<String> getAuctionCode() {
        String o = request.getParameter("o");
        if (null == o) {
            GeneralResult<String> data = new GeneralResult<>();
            data.setSuccess(false);
            data.setValue("请输入淘宝app分享链接 或者 淘宝商品链接");

            DeferredResult<String> result = new DeferredResult<>();
            result.setResult(JSON.toJSONString(data));
            return result;
        }

        try {

            String id = "";

            log.info("用户输入 => " + o);

            Pattern domain = Patterns.DOMAIN_NAME;

            // 查找用户输入文字中的url
            Pattern webUrl = Patterns.WEB_URL;
            Matcher matcher = webUrl.matcher(o);
            if (matcher.find()) {
                String url = matcher.group(0);
                log.info("找到包含到链接 => " + url);
                // 判断域名是否为 item.taobao.com / detail.tmall.com
                matcher = domain.matcher(url);
                if (matcher.find()) {
                    if (StringUtils.contains(matcher.group(0), tbDomain) || StringUtils.contains(matcher.group(0), tmDomain)) {
                        // 直接进行url解析 若有id就不必使用selenium
                        MultiMap<String> params = URLs.urlParser(url);
                        if (null != params) {
                            List<String> values = params.get("id");
                            if (CollectionUtils.isNotEmpty(values)) {
                                id = values.get(0);
                                log.info("id => " + id);
                            }
                        }
                    }
                }

                if (StringUtils.isBlank(id)) {
                    log.info("flag: 用户输入的不包含长链接");
                    // 如果不是长链接 则用浏览器打开短链接 再解析
                    Chromes.openTabAndFocus(url);
                    if (Chromes.scanTbUrl()) {
                        url = Chromes.getCurrentUrl();
                        log.info("解析的长链接 => " + url);
                        // 判断域名
                        matcher = domain.matcher(url);
                        if (matcher.find()) {
                            if (StringUtils.contains(matcher.group(0), tbDomain) || StringUtils.contains(matcher.group(0), tmDomain)) {
                                // 直接进行url解析
                                MultiMap<String> params = URLs.urlParser(url);
                                if (null != params) {
                                    List<String> values = params.get("id");
                                    if (CollectionUtils.isNotEmpty(values)) {
                                        id = values.get(0);
                                        log.info("id => " + id);
                                    }
                                }
                            }
                        }
                    }
                    Chromes.close();
                }
            }

            if (StringUtils.isBlank(id)) {
                log.info("flag: 用户输入的不包含任何链接");
                // 尝试截取淘口令
                String s = StringUtils.substringBetween(o, "€");
                if (StringUtils.isNotBlank(s)) {
                    String taoToken = "€" + s + "€";
                    log.info("淘口令 => " + taoToken);
                    String url = Alimama.tklParser(taoToken);
                    log.info("淘口令转长链接 => " + url);

                    if (StringUtils.isNotBlank(url)) {
                        Chromes.openTabAndFocus(url);
                        if (Chromes.scanTbUrl()) {
                            url = Chromes.getCurrentUrl();
                            log.info("解析的长链接 => " + url);
                            // 判断域名
                            matcher = domain.matcher(url);
                            if (matcher.find()) {
                                if (StringUtils.contains(matcher.group(0), tbDomain) || StringUtils.contains(matcher.group(0), tmDomain)) {
                                    // 直接进行url解析
                                    MultiMap<String> params = URLs.urlParser(url);
                                    if (null != params) {
                                        List<String> values = params.get("id");
                                        if (CollectionUtils.isNotEmpty(values)) {
                                            id = values.get(0);
                                            log.info("id => " + id);
                                        }
                                    }
                                }
                            }
                        }
                        Chromes.close();
                    } else {
                        GeneralResult<String> data = new GeneralResult<>();
                        data.setSuccess(false);
                        data.setValue("请输入正确的淘口令");

                        DeferredResult<String> result = new DeferredResult<>();
                        result.setResult(JSON.toJSONString(data));
                        return result;
                    }
                }
            }

            if (StringUtils.isNotBlank(id)) {
                AuctionCode.Codes codes = Alimama.getAuctionCode(id);
                if (null != codes) {
                    GeneralResult<AuctionCode.Codes> data = new GeneralResult<>();
                    data.setSuccess(true);
                    data.setValue(codes);

                    DeferredResult<String> result = new DeferredResult<>();
                    result.setResult(JSON.toJSONString(data));
                    return result;
                } else {
                    GeneralResult<String> data = new GeneralResult<>();
                    data.setSuccess(false);
                    data.setValue("该商品不在计划中");

                    DeferredResult<String> result = new DeferredResult<>();
                    result.setResult(JSON.toJSONString(data));
                }
            }

            GeneralResult<String> data = new GeneralResult<>();
            data.setSuccess(false);
            data.setValue("发生内部错误");

            DeferredResult<String> result = new DeferredResult<>();
            result.setResult(JSON.toJSONString(data));
            return result;

        } catch (Exception e) {
            e.printStackTrace();

            Chromes.close();

            GeneralResult<String> data = new GeneralResult<>();
            data.setSuccess(false);
            data.setValue(e.getMessage());

            DeferredResult<String> result = new DeferredResult<>();
            result.setResult(JSON.toJSONString(data));
            return result;
        }

    }
}
