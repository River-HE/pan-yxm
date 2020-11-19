package tech.yxm.pan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tech.yxm.pan.config.PanProperties;

/**
 * @author river
 * @date 2020/11/17 16:11:36
 * @description
 */

@SpringBootApplication
@EnableConfigurationProperties({
        PanProperties.class
})
public class PanYxmApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanYxmApplication.class, args);
    }

}
