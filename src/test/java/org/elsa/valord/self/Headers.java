package org.elsa.valord.self;

import java.util.Map;

/**
 * @author valord577
 * @date 18-8-29 下午3:27
 */
public class Headers {

    public static void main(String[] args) {

        org.elsa.valord.common.utils.Headers headers = org.elsa.valord.common.utils.Headers.getInstance();
        Map map = headers.getHeader(org.elsa.valord.common.utils.Headers.TB);

        boolean f = headers.putCookie(org.elsa.valord.common.utils.Headers.TB, "123");
        if (f) {
            System.out.println(map.get("cookie"));
        }
    }
}
