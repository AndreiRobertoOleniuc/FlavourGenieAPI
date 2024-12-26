package ch.flavourgenie.flavourgenieapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FlavourGenieApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlavourGenieApiApplication.class, args);
    }

}
