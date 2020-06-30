package io.kischang.kispiano.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author KisChang
 * @date 2020-06-30
 */
@SpringBootApplication
@ComponentScan("io.kischang.kispiano")
@EntityScan("io.kischang.kispiano")
@EnableJpaRepositories("io.kischang.kispiano")
public class AdminMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminMainApplication.class, args);
    }

}
