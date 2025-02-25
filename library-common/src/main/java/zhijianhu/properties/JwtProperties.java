package zhijianhu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "library.jwt")
@Data
public class JwtProperties {
    private long userTtl;
}
