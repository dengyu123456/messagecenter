package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.ChatGroup;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.respone.ChatGroupVO;
import com.zhkj.nettyserver.message.service.MessageService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Des:获取群Handler
 * ClassName: ListGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:19
 */
public class ListGroupHandler extends SimpleChannelInboundHandler<Long> {
    MessageService messageService = null;

    public ListGroupHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long suseUuid) throws Exception {
        listGroup(suseUuid, ctx.channel());
    }
    private void listGroup(Long suseUuid, Channel channel) {
        if (suseUuid == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定用户", "listGroup"));
            return;
        }
        List<ChatGroup> cGroupList = this.messageService.selectChatGroup(suseUuid);
        List<ChatGroupVO> voList = new ArrayList<ChatGroupVO>();
        if (!CollectionUtils.isEmpty(cGroupList)) {
            User user = null;
            for (ChatGroup chat : cGroupList) {
                ChatGroupVO vo = new ChatGroupVO();
                BeanUtil.copyProperties(chat, vo);
//                获取群成员的并返回
                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
                vo.setChatGroupUserList(chatGroupUserList);
                user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
                vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
                voList.add(vo);
            }
        }
        channel.writeAndFlush(ResponseStompFactory.createOk(voList, "listGroup"));
    }
}
