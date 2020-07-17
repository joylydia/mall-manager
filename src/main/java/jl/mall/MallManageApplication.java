
package jl.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author joy
 */
@MapperScan(basePackages = "jl.mall.dao")
@ComponentScan(basePackages = "jl.mall")
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
public class MallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallManageApplication.class, args);
    }

}
