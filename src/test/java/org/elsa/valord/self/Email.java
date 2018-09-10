package org.elsa.valord.self;


import org.elsa.valord.common.utils.Emails;

/**
 * @author valord577
 * @date 18-8-29 下午5:16
 */
public class Email {

    public static void main(String[] args) {
        String[] sp = "m13094955432@163.com".split(",");
        for (int i = 0; i < sp.length; i++) {
            System.out.println(sp[i]);

        }

        System.out.println(Emails.send("测试", "测试"));

    }

}
