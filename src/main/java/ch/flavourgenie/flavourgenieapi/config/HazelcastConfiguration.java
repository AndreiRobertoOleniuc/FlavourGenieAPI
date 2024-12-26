package ch.flavourgenie.flavourgenieapi.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {
    @Bean
    public Config hazelcastConfig() {
        return new Config()
                .addMapConfig(new MapConfig()
                        .setName("default")
                        .setTimeToLiveSeconds(3600)); // Entries expire after 1 hour
    }
}
