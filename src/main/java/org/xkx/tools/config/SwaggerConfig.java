package org.xkx.tools.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
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
 * Swagger2的接口配置
 *
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {
   /**
    * 创建API
    */
   @Bean
   public Docket createRestApi() {
      return new Docket(DocumentationType.SWAGGER_2)
            // 详细定制
            .apiInfo(apiInfo()).select()
            // 指定当前包路径
            .apis(RequestHandlerSelectors.basePackage("org.xkx.tools.controller"))
            // 扫描所有 .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any()).build();
   }

   /**
    * 添加摘要信息
    */
   private ApiInfo apiInfo() {
      // 用ApiInfoBuilder进行定制
      return new ApiInfoBuilder().title("接口文档")
                           .description("描述")
                           .contact(new Contact("系统名称", null, null))
                           .version("版本号:V1.0.0")
                           .build();
   }
}
