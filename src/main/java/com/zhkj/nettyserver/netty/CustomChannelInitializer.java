package com.zhkj.nettyserver.netty;

import com.zhkj.nettyserver.netty.handler.CodecHandler;
import com.zhkj.nettyserver.netty.handler.LoginHandler;
import com.zhkj.nettyserver.netty.handler.SpliterHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author dengyi
 * time：2019年6月10日 17:19:46
 * 组装handler
 */
public class CustomChannelInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("http-codec", new HttpServerCodec()); // Http消息编码解码
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
        pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
      //  pipeline.addLast("handler",new CustomHandler());//

        pipeline.addLast("login",new LoginHandler());
        pipeline.addLast("codec",new CodecHandler());
        pipeline.addLast("split",new SpliterHandler());

    }
}
