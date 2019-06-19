package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.request.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Des:分发Handler
 * ClassName: SpliterHandler
 * Author: dengyi
 * Date: 2019-06-18 16:42
 */
public class SpliterHandler extends SimpleChannelInboundHandler<Object> {

    private Map<String, SimpleChannelInboundHandler> handlerMap;

    public SpliterHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put("chat", new ChatHandler());
        handlerMap.put("chatOpen", new ChatOpenHandler());
        handlerMap.put("chatList", new ChatListHandler());
        handlerMap.put("listFriend", new ListFriendHandler());
        handlerMap.put("openGroup2",new OpenGroup2Handler());
        handlerMap.put("listGroup",new  ListGroupHandler());
        handlerMap.put("outGroup",new OutGroupHandler());
        handlerMap.put("addGroup",new AddGroupHandler());
        handlerMap.put("delGroup",new DelGroupHandler());
        handlerMap.put("editGroup",new EditGroupHandler());
        handlerMap.put("editGroupUser",new EditGroupUserHandler());
        handlerMap.put("listMsg",new ListMsgHandler());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Request) {
            Request reOb = (Request) msg;
            //根据 reOb.getAction(); 进行分发
            handlerMap.get(reOb.getAction()).channelRead(ctx, reOb.getParams());
        }
    }
}
