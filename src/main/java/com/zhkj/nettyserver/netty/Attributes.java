package com.zhkj.nettyserver.netty;

import io.netty.util.AttributeKey;

/**
 /**
 * @author dengyi
 * time:2019年6月11日 10:31:17
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN=AttributeKey.valueOf("LOGIN");
    AttributeKey<Session> SESSION=AttributeKey.valueOf("SESSION");
}
