package org.elsa.valord.common.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author valor
 * @date 2018/9/5 18:14
 */
@Configuration
@EnableSwagger2
public class Swagger2Conf {

    @Bean
    public Docket createRestApiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.elsa.valord.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private Contact contact() {
        return new Contact("valor", "https://github.com/valord577", "valord577@gmail.com");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("商品导流 项目 Restful APIs")
                .description("")
                .contact(contact())
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
