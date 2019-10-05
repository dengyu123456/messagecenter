package com.zhkj.nettyserver;

import com.zhkj.nettyserver.common.util.redis.CustomPubSub;
import com.zhkj.nettyserver.common.util.redis.RedisUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreshbossApplicationTests {

	@Autowired
	RedisUtil redisUtil;

	@Test
	public void contextLoads() {
	redisUtil.publish("test","这是测试");

	redisUtil.psubscribe("test",new CustomPubSub(){
		@Override
		public void onPMessage(String pattern, String channel, String message) {
			super.onPMessage(pattern, channel, message);
			System.out.println(message);
		}
	});
	}

}

