package com.zhkj.nettyserver.netty.handler;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.request.Request;
import com.zhkj.nettyserver.message.domain.request.*;
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
public class SpliterHandler extends SimpleChannelInboundHandler<Request> {

    private Map<String, SimpleChannelInboundHandler> handlerMap;

    //解码映射表
    Map<String, Class> codecMap = null;

    public SpliterHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put("chat", new ChatHandler());
        handlerMap.put("chatOpen", new ChatOpenHandler());
        handlerMap.put("chatList", new ChatListHandler());
        handlerMap.put("listFriend", new ListFriendHandler());
        handlerMap.put("openGroup2", new OpenGroup2Handler());
        handlerMap.put("listGroup", new ListGroupHandler());
        handlerMap.put("outGroup", new OutGroupHandler());
        handlerMap.put("updateGroup",new EditUpdateGroupUserHandler());
        handlerMap.put("editGroup", new EditGroupHandler());
        handlerMap.put("editGroupUser", new EditGroupUserHandler());
        handlerMap.put("listMsg", new ListMsgHandler());
        handlerMap.put("schat", new SchatHandler());

        codecMap = new HashMap<String, Class>();

        codecMap.put("chat", MessageParams.class);
        codecMap.put("chatOpen", OpenParams.class);
        codecMap.put("listFriend", Long.class);
        codecMap.put("chatList", Long.class);
        codecMap.put("openGroup2", OpenGroupParams2.class);
        codecMap.put("listGroup", Long.class);
        codecMap.put("outGroup", OutGroupParams.class);
        codecMap.put("editGroup", EditChatGroupParams.class);
        codecMap.put("editGroupUser", EditChatGroupUserParams.class);
        codecMap.put("listMsg", SearchMessageParams.class);
        codecMap.put("updateGroup", EditUpdateChatGroupParams.class);
        codecMap.put("schat", SChatParams.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Request reOb) throws Exception {
        Object msg = JSON.parseObject(reOb.getParams(), codecMap.get(reOb.getAction()));
        handlerMap.get(reOb.getAction()).channelRead(ctx, msg);
    }

}
