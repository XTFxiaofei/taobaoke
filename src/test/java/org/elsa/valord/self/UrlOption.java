package org.elsa.valord.self;

import org.eclipse.jetty.util.MultiMap;
import org.elsa.valord.common.utils.URLs;

/**
 * @author valor
 * @date 2018/9/9 21:51
 */
public class UrlOption {

    public static void main(String[] args) {
        MultiMap<String> map = URLs.urlParser("https://www.google.com/search?s=123&s=345");
        System.out.println(map.getString("s"));
    }

}
