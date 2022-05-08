package cn.eziliao.demo.springbootrabbitmq.delayed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chengli
 */
@SpringBootApplication
public class DelayedStartup {
    public static void main(String[] args) {
        SpringApplication.run(DelayedStartup.class, args);
    }
}
