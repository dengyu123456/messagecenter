package com.zhkj.nettyserver.netty.handler;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.ChatGroup;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.request.OpenGroupParams2;
import com.zhkj.nettyserver.message.domain.respone.OpenGroupVO;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Des:开启群Handler
 * ClassName: OpenGroup2Handler
 * Author: dengyi
 * Date: 2019-06-18 17:21
 */
public class OpenGroup2Handler extends SimpleChannelInboundHandler<OpenGroupParams2> {
    MessageService messageService = null;

    public OpenGroup2Handler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OpenGroupParams2 params) throws Exception {
        openGroup2(params, ctx.channel());
    }

    private void openGroup2(OpenGroupParams2 ogp2, Channel channel) {
        if (ogp2 == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定创建群的参数", "openGroup2"));
            return;
        }
        if (!StringUtil.isBlank(ogp2.getCgorName())) {
            if (ogp2.getCgorName().length() < 1 || ogp2.getCgorName().length() > 64) {
                channel.writeAndFlush(ResponseStompFactory.createBad("会话名字<1~64位>", "openGroup2"));
                return;
            }
        }
        if (ogp2.getsSuseUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定创建群用户", "openGroup2"));
            return;
        }
        if (ogp2.geteSuseUuid().isEmpty() || ogp2.geteSuseUuid().size() < 1 || ogp2.geteSuseUuid().size() > 255) {
            channel.writeAndFlush(ResponseStompFactory.createBad("参与群用户<1~255人>", "openGroup2"));
            return;
        }

        ChatGroup chatGroup = this.messageService.insertGroup(ogp2);
        User cgroCsuse = this.messageService.selectUserByUserUuid(chatGroup.getCgroCsuseUuid());
        List<Long> userList = ogp2.geteSuseUuid();
        OpenGroupVO vo = new OpenGroupVO();
        BeanUtil.copyProperties(chatGroup, vo);
        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chatGroup.getCgroUuid());
        vo.setChatGroupUserList(chatGroupUserList);
        vo.setCgroCsuseName(cgroCsuse.getUserName());
        for (Long user : userList) {
            Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, user);
            if (otherChannel != null && otherChannel.isActive()) {
                otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "openGroup2"));
            }
        }
        channel.writeAndFlush(ResponseStompFactory.createOk(vo, "openGroup2"));
    }
}
