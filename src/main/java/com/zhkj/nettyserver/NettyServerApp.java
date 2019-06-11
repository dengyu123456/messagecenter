package com.zhkj.nettyserver;

import com.zhkj.nettyserver.netty.NettyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author dengyi
 * time:2019年6月11日 10:31:17
 */
@SpringBootApplication
public class NettyServerApp implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(NettyServerApp.class, args);
    }

    //实现CommandLineRunner 重写run方法 这里加了netty的启动
    @Override
    public void run(String... args) throws Exception {
        new NettyService();
    }
}
