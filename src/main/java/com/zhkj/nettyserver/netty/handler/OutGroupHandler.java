package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.message.domain.ChatGroup;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.request.OutGroupParams;
import com.zhkj.nettyserver.message.domain.respone.DissGroupVO;
import com.zhkj.nettyserver.message.domain.respone.OutGroupVo;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Des:退出群Handler
 * ClassName: OutGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:17
 */
public class OutGroupHandler extends SimpleChannelInboundHandler<OutGroupParams> {

    MessageService messageService = null;

    public OutGroupHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OutGroupParams params) throws Exception {
        outGroup(params, ctx.channel());
    }

    private void outGroup(OutGroupParams ogpa, Channel channel) {
        /**
         * 本身才能退出
         * 群人数需要减少
         * 退出会话
         * 会话总人数减少
         *
         * 如果是群主退群，就按照加群顺序修改为群主
         */
        if (ogpa == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定退出群信息", "outGroup"));
            return;
        }

        if (ogpa.getCgusSuseUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定退出群的用户", "outGroup"));
            return;
        }
        if (ogpa.getCgusCgroUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定群号码", "outGroup"));
            return;
        }
        Long chatGroupChatUuid = null;
        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(ogpa.getCgusCgroUuid());
        ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(ogpa.getCgusCgroUuid());
        User user = this.messageService.selectUserByUserUuid(ogpa.getCgusSuseUuid());
        try {
//          User user = this.messageService.selectUserByUserUuid(ogpa.getCgusSuseUuid());
            chatGroupChatUuid = this.messageService.outGroup(ogpa);
        } catch (Exception e) {
            //  LOGGER.info(e.getMessage());
            //如果退群的是群主
            if (ogpa.getCgusSuseUuid().equals(chatGroup.getCgroCsuseUuid())) {
                DissGroupVO vo = new DissGroupVO();
                vo.setCgroChatUuid(chatGroup.getCgroChatUuid());
                vo.setCgroName(chatGroup.getCgroName());
                vo.setCgroUuid(ogpa.getCgusCgroUuid());
                for (ChatGroupUser item : chatGroupUserList) {
                    Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, item.getCgusSuseUuid());
                    if (otherChannel!=null&&otherChannel.isActive()) {
                    otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "dissGroup"));
                    }
                }
            } else {
                channel.writeAndFlush(ResponseStompFactory.createBad(e.getMessage(), "delGroup"));
            }
        }
        OutGroupVo vo = new OutGroupVo();
        vo.setCgroChatUuid(chatGroupChatUuid);
        vo.setCgroUuid(ogpa.getCgusCgroUuid());
        vo.setCgroSuseName(user.getUserName());
        vo.setCgusSuseUuid(user.getUserUuid());
        vo.setCgroName(chatGroup.getCgroName());
        for (ChatGroupUser item : chatGroupUserList) {
            Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, item.getCgusSuseUuid());
            if (otherChannel!=null&&otherChannel.isActive()){
                 otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "outGroup"));
            }
        }
    }
}
