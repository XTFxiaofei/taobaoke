package org.elsa.valord.common.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * cookie管理类
 *
 * @author valord577
 * @date 18-8-21 下午5:22
 */
public class ReqHeader {

    /**
     * TB 请求头缓存
     */
    private static final Map<String, String> TB_HEADERS = new HashMap<>();

    /**
     * JD 请求头缓存
     */
    private static final Map<String, String> JD_HRADERS = new HashMap<>();

    /**
     * TB cookie缓存
     */
    private static String TB_COOKIE = null;

    /**
     * JD cookie缓存
     */
    private static String JD_COOKIE = null;

    /**
     * 指定参数
     */
    public static final int TB = 0;
    public static final int JD = 1;

    /**
     * 私有对象 非懒汉模式
     */
    private static ReqHeader cacheManager = null;

    /**
     * 模拟浏览器请求头
     */
    private static final String ACCEPT = "application/json, text/javascript, */*; q=0.01";
    private static final String ACCEPT_ENCODING = "gzip, deflate, br";
    private static final String ACCEPT_LANGUAGE = "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7";
    private static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36";

    /**
     * 私有构造 外部不可new
     */
    private ReqHeader() {
        TB_HEADERS.put("accept", ACCEPT);
        TB_HEADERS.put("accept-encoding", ACCEPT_ENCODING);
        TB_HEADERS.put("accept-language", ACCEPT_LANGUAGE);
        TB_HEADERS.put("user-agent", USER_AGENT);

        JD_HRADERS.put("accept", ACCEPT);
        JD_HRADERS.put("accept-encoding", ACCEPT_ENCODING);
        JD_HRADERS.put("accept-language", ACCEPT_LANGUAGE);
        JD_HRADERS.put("user-agent", USER_AGENT);
    }

    /**
     * 允许外部获得实例对象
     */
    public static ReqHeader getInstance() {
        if (null == cacheManager) {
            cacheManager = new ReqHeader();
        }

        return cacheManager;
    }

    /**
     * 获取 headers
     */
    public Map<String, String> getHeader(int type) {
        switch (type) {
            case TB:
                return TB_HEADERS;
            case JD:
                return JD_HRADERS;

            default:
                return null;
        }
    }

    /**
     * 更新 cookie
     */
    public boolean putCookie(int type, String cookie) {
        if (null == cookie) {
            return false;
        }

        switch (type) {
            case TB:
                if (null == TB_COOKIE) {
                    TB_COOKIE = cookie;
                    TB_HEADERS.put("cookie", TB_COOKIE);
                    return true;
                }

                if (cookie.equals(TB_COOKIE)) {
                    return false;
                }

                TB_COOKIE = cookie;
                TB_HEADERS.put("cookie", TB_COOKIE);
                return true;

            case JD:
                if (null == JD_COOKIE) {
                    JD_COOKIE = cookie;
                    JD_HRADERS.put("cookie", JD_COOKIE);
                    return true;
                }

                if (cookie.equals(JD_COOKIE)) {
                    return false;
                }

                JD_COOKIE = cookie;
                JD_HRADERS.put("cookie", JD_COOKIE);
                return true;

            default:
                return false;
        }
    }


}
