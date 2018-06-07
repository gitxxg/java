package cn.ghl.sbsc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
//@ComponentScan("cn.ghl.sbsc.server")
public class SbscServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbscServerApplication.class, args);
    }

}
