package org.elsa.valord.self;

import org.elsa.valord.common.pojo.HttpResult;
import org.elsa.valord.common.utils.ToMaps;

import java.util.Map;
import java.util.Scanner;

/**
 * @author valord577
 * @date 18-8-21 下午6:40
 */
public class ToMap {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        int i = input.nextInt();

        sw(i);

        System.out.println(i);

    }

    private static void sw(int i) throws Exception {
        switch (i) {
            case 0:
                i = 10;
            case 1:
                i = 11;

            default:
                throw new Exception("11");
        }
    }


}
