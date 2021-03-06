package sign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author 邝明山
 * on 2020/10/29 15:35
 */

@SpringBootApplication
@ServletComponentScan("config.filters")
public class SignSystem {
    public static void main(String[] args) {
        SpringApplication.run(SignSystem.class, args);
    }
}
