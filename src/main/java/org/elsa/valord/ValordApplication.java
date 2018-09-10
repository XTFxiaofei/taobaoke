package org.elsa.valord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author valord577
 * @date 18-8-28 下午5:04
 */
@SpringBootApplication
@EnableScheduling
public class ValordApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValordApplication.class, args);
        System.out.println("======  app running. ======");
    }
}
