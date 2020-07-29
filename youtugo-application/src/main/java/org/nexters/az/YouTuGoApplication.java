package org.nexters.az;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "org.nexters.az")
public class YouTuGoApplication {
    public static void main(String[] args){
        SpringApplication.run(YouTuGoApplication.class,args);
    }
}