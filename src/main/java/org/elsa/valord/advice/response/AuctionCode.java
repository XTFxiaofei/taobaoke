package org.elsa.valord.advice.response;

import lombok.Data;

/**
 * @author valor
 * @date 2018/9/10 11:02
 */
@Data
public class AuctionCode {

    /**
     * 查询成功为null
     */
    private String invalidKey;

    /**
     * 查询操作是否成功
     */
    private boolean ok;

    /**
     * 优惠券 下单链接信息
     */
    private Codes data;

    /**
     * 暂时不知道啥用
     */
    private Info info;

    @Data
    public static class Codes {

        /**
         * 下单链接 长链接
         */
        private String clickUrl;

        /**
         * 优惠券链接 长链接
         */
        private String couponLink;

        /**
         * 暂时不知道啥用
         */
        private String tkCommonRate;

        /**
         * 下单链接 淘口令
         */
        private String taoToken;

        /**
         * 优惠券链接 淘口令
         */
        private String couponLinkTaoToken;

        /**
         * 下单链接 二维码
         */
        private String qrCodeUrl;

        /**
         * 阿里妈妈标识字段
         */
        private String type;

        /**
         * 优惠券链接 短链接
         */
        private String couponShortLinkUrl;

        /**
         * 下单链接 短链接
         */
        private String shortLinkUrl;
    }

    @Data
    public static class Info {

        private boolean ok;

        private String message;
    }
}
