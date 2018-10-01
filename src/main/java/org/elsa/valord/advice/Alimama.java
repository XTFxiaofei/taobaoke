package org.elsa.valord.advice;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.elsa.valord.advice.response.AuctionCode;
import org.elsa.valord.advice.response.TklCode;
import org.elsa.valord.common.pojo.HttpResult;
import org.elsa.valord.common.utils.Headers;
import org.elsa.valord.common.utils.Https;

import java.util.HashMap;
import java.util.Map;

/**
 * @author valor
 * @date 2018/9/9 23:24
 */
@Slf4j
public class Alimama {

    private static Headers headers = Headers.getInstance();

    private static final String AUCTION_CODE = "https://pub.alimama.com/common/code/getAuctionCode.json";

    private static final String TKL_PARSER = "http://api.chaozhi.hk/tb/tklParse";

    /**
     * @param id 淘宝商品id
     * @return 获取优惠券、下单链接
     */
    public static AuctionCode.Codes getAuctionCode(String id) {
        Map<String, String> queryParams = new HashMap<>(8);
        queryParams.put("auctionid", id);
        queryParams.put("adzoneid", "129200277");
        queryParams.put("siteid", "208910108");
        queryParams.put("scenes", "1");

        try {
            HttpResult result = Https.ofGet(AUCTION_CODE, headers.getHeader(Headers.TB), queryParams);
            log.info("Alimama httpResult => " + result);
            JsonParser jp = new JsonParser();
            //将json字符串转化成json对象
            JsonObject jo = jp.parse(result.getContent()).getAsJsonObject();
            //获取message对应的值
            //String data = jo.get("data").getAsString();

            log.info("判断是否能找到:"+jo.get("data"));
            //if (200 == result.getCode() && null != result.getContent()) {
            if(200==result.getCode()&&null!=jo.get("data")){
                AuctionCode auctionCode = JSON.parseObject(result.getContent(), AuctionCode.class);
                if (auctionCode.isOk() && null != auctionCode.getData()) {
                    return auctionCode.getData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 表示没有请求到数据
        return null;
    }

    /**
     * @param taoToken 淘口令
     * @return 将淘口令转换成长链接
     */
    public static String tklParser(String taoToken) {
        Map<String, String> map = new HashMap<>(4);
        map.put("tkl", taoToken);

        try {
            HttpResult result = Https.ofPost(TKL_PARSER, map);
            log.info("httpResult => " + result);
            if (200 == result.getCode() && null != result.getContent()) {
                TklCode tklCode = JSON.parseObject(result.getContent(), TklCode.class);
                if (0 == tklCode.getError_code() && null != tklCode.getData()) {
                    TklCode.TklUrl tklUrl = JSON.parseObject(JSON.toJSONString(tklCode.getData()), TklCode.TklUrl.class);
                    if (tklUrl.isSuc()) {
                        return tklUrl.getUrl();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 表示没有请求到数据
        return null;
    }

}
