package com.zhkj.nettyserver.netty.handler;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.ChatGroup;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.request.AddChatGroupUserParams;
import com.zhkj.nettyserver.message.domain.respone.ChatGroupVO;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Des:拉人进群Handler
 * ClassName: AddGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:16
 */
public class AddGroupHandler extends SimpleChannelInboundHandler<AddChatGroupUserParams> {
    MessageService messageService = (MessageService) SpringUtil.getBean("messageService");
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddChatGroupUserParams params) throws Exception {

    }
//    private void addGroup(String params, Channel channel) {
//        /**
//         * 增加人
//         * 群和增加人在同一个圈里面
//         * 被增加人必须激活
//         * 群人数需要增加
//         * 增加会话人
//         * 会话人数需要增加
//         */
//        if (StringUtil.isBlank(params)) {
//            sendWebSocket(channel, ResponseStompFactory.createBad("请群增加人信息", "addGroup"));
//            return;
//        }
//        AddChatGroupUserParams agup = JSON.parseObject(params, AddChatGroupUserParams.class);
//
//        if (agup.getCgusSuseUuid() == null) {
//            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群参与用户Uuid", "addGroup"));
//            return;
//        }
//        if (agup.getUserUuid() == null) {
//            sendWebSocket(channel, ResponseStompFactory.createBad("请指定被拉人Uuid", "addGroup"));
//            return;
//        }
//        if (agup.getCgroUuid() == null) {
//            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群Uuid", "addGroup"));
//            return;
//        }
//        try {
//            int count = this.messageService.addChatGroupUser(agup);
//        } catch (Exception e) {
//            //   LOGGER.error(e.getMessage());
//            sendWebSocket(channel, ResponseStompFactory.createOk("新增失败", "addGroup"));
//        } finally {
//            ChatGroup cGroup = this.messageService.selectChatGroupByCgroUuid(agup.getCgroUuid());
//            if (cGroup != null) {
//                User user = null;
//                ChatGroup chat = cGroup;
//                ChatGroupVO vo = new ChatGroupVO();
//                BeanUtil.copyProperties(chat, vo);
////                获取群成员的并返回
//                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
//                vo.setChatGroupUserList(chatGroupUserList);
//                user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
//                vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
//                for (ChatGroupUser item : chatGroupUserList) {
//                    sendWebSocket(ChannelUtil.getInstance().getChannel(item.getCgusSuseUuid()), ResponseStompFactory.createOk(vo, "addGroup"));
//                }
//            }
//        }
//
//
//    }

}
