package org.elsa.valord.self;

import org.elsa.valord.common.pojo.HttpResult;
import org.elsa.valord.common.utils.Https;

import java.util.HashMap;
import java.util.Map;

/**
 * @author valor
 * @date 2018/9/10 13:26
 */
public class ApiTest {

    public static void main(String[] args) throws Exception {

        Map<String, String> map = new HashMap<>(4);
        map.put("tkl", "€oPeeb3oRWf3€");
        HttpResult result = Https.ofPost("http://api.chaozhi.hk/tb/tklParse", map);
        System.out.println(result);

    }
}
