package my.project.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/9/12 上午8:34
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
public class WhiteUrlsConfig {

    private List<String> urls = new ArrayList<>();

}
