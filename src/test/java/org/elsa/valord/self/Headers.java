package org.elsa.valord.self;

import org.elsa.valord.common.tools.ReqHeader;

import java.util.Map;

/**
 * @author valord577
 * @date 18-8-29 下午3:27
 */
public class Headers {

    public static void main(String[] args) {

        ReqHeader reqHeader = ReqHeader.getInstance();
        Map map = reqHeader.getHeader(ReqHeader.TB);

        boolean f = reqHeader.putCookie(ReqHeader.TB, "123");
        if (f) {
            System.out.println(map.get("cookie"));
        }
    }
}
