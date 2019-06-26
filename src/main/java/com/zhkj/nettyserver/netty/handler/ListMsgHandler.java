package com.zhkj.nettyserver.netty.handler;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.ChatUser;
import com.zhkj.nettyserver.message.domain.Message;
import com.zhkj.nettyserver.message.domain.request.SearchMessageParams;
import com.zhkj.nettyserver.message.domain.respone.SearchMessageVO;
import com.zhkj.nettyserver.message.service.MessageService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Des:历史聊天记录Handler
 * ClassName: ListMsgHandler
 * Author: dengyi
 * Date: 2019-06-18 17:10
 */
public class ListMsgHandler extends SimpleChannelInboundHandler<SearchMessageParams> {
    MessageService messageService = null;

    public ListMsgHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SearchMessageParams params) throws Exception {
        listMsg(params, ctx.channel());
    }

    private void listMsg(SearchMessageParams smpa, Channel channel) {
        if (smpa == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定获取会话列表参数", "listMsg"));
            return;
        }

        //参数校验
        if (smpa.getMessChatUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定会话", "listMsg"));
            return;
        }
        if (smpa.getMessEndDate() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定消息结束时间", "listMsg"));
            return;
        }
        if (smpa.getMessStartDate() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定消息开始时间", "listMsg"));
            return;
        }

        List<Message> messageList = this.messageService.selectMessage(smpa);
        List<SearchMessageVO> voList = new ArrayList<SearchMessageVO>();
        if (!CollectionUtils.isEmpty(messageList)) {
            List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(smpa.getMessChatUuid());
            Map<Long, String> chatUserMap = new HashMap<Long, String>();
            for (ChatUser cItem : chatUserList) {
                chatUserMap.put(cItem.getCuseSuseUuid(), cItem.getCuseName());
            }
            for (Message item : messageList) {
                SearchMessageVO vo = new SearchMessageVO();
                BeanUtil.copyProperties(item, vo);
                vo.setMessSuseName(chatUserMap.containsKey(vo.getMessSuseUuid()) ? chatUserMap.get(vo.getMessSuseUuid()) : "未知");
                voList.add(vo);
            }
        }
        channel.writeAndFlush(ResponseStompFactory.createOk(voList, "listMsg"));
    }
}
