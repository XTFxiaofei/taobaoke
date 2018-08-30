package org.elsa.valord.self;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.elsa.valord.common.utils.ToMaps;

import java.util.HashMap;
import java.util.Map;

/**
 * @author valord577
 * @date 18-8-29 下午1:09
 */
public class ToJson {

    public static void main(String[] args) {
        Data.Te te = new Data.Te();
        te.setB("bbb");

        Data data = new Data();
        data.setA("aaa");
        data.setT(te);

        Map<String, String> map = ToMaps.convertToMap(data, 4);

        Map<String, Object> t = new HashMap<>(4);
        t.put("b", "bbb");

        Map<String, Object> h = new HashMap<>(4);
        h.put("a", "aaa");
        h.put("t", t);

        Gson gson = new Gson();

        System.out.println(gson.toJson(map));
        System.out.println(gson.toJson(data));
        System.out.println(gson.toJson(h));

        Map<String, Object> x = JSON.parseObject(JSON.toJSONString(data));
        System.out.println(x.getClass());
    }

    @lombok.Data
    private static class Data {
        private String a;
        private Te t;

        @lombok.Data
        static class Te {
            private String b;
        }

    }

}
