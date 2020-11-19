package tech.yxm.pan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author river
 * @date 2020/11/17 16:11:36
 * @description
 */

@Component
@ConfigurationProperties(prefix = "pan")
@Data
public class PanProperties {

    private String baseDir;
}
