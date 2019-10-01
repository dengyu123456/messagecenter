package com.zhkj.nettyserver.netty.handler;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.request.Request;
import com.zhkj.nettyserver.common.base.respone.ResponseStomp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * Des:编码解码Handler
 * ClassName: CodecHandler
 * Author: dengyi
 * Date: 2019-06-21 09:11
 */
public class CodecHandler extends MessageToMessageCodec<TextWebSocketFrame, ResponseStomp> {


    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame twsf, List<Object> in) throws Exception {//解码
        String text = twsf.text();
        Request request = JSON.parseObject(text, Request.class);
        in.add(request);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseStomp rsto, List<Object> out) throws Exception {//编码
        out.add(new TextWebSocketFrame(JSON.toJSONString(rsto)));
    }

}
