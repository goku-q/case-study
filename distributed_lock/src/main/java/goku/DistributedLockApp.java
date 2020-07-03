package goku;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 模拟抢购订单分布式锁，启动引导类
 */
@SpringBootApplication
@Configuration
public class DistributedLockApp {
    public static void main(String[] args) {
        SpringApplication.run(DistributedLockApp.class,args);
    }

    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.0.9:6379").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}
