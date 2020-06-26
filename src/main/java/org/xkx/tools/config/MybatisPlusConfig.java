package org.xkx.tools.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@MapperScan(value = {"org.xkx.**.dao"})
@Configuration
public class MybatisPlusConfig {

   /**
    * mybatis-plus分页插件<br>
    * 文档：http://mp.baomidou.com<br>
    */
   @Bean
   public PaginationInterceptor paginationInterceptor() {
      return new PaginationInterceptor();
   }

}