package org.elsa.valord.self;

import org.apache.commons.lang3.StringUtils;

/**
 * @author valor
 * @date 2018/9/10 16:13
 */
public class Tkl {

    public static void main(String[] args) {

        String tkl = "€WVlPb3FGuXO€";

        String s = StringUtils.substringBetween(tkl, "€");
        System.out.println(s);
    }
}
