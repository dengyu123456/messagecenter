package com.zhkj.purchase.netty;

import io.netty.util.AttributeKey;

/**
 * Created by root on 2019/6/10.
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN=AttributeKey.valueOf("LOGIN");
}
