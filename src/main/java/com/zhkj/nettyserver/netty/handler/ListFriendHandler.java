package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.respone.UserVO;
import com.zhkj.nettyserver.message.service.MessageService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Des:拉取好友Handler
 * ClassName: ListFriendHandler
 * Author: dengyi
 * Date: 2019-06-18 17:22
 */
public class ListFriendHandler extends SimpleChannelInboundHandler<Long> {
    MessageService messageService = null;

    public ListFriendHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long suseUuid) throws Exception {
        listFriend(suseUuid, ctx.channel());
    }

    private void listFriend(Long suseUuid, Channel channel) {
        if (suseUuid == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定用户", "listFriend"));
            return;
        }
        User user = this.messageService.selectBySuseUuid(suseUuid, false);
        if (user == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("获取好友列表失败，该用户不存在", "listFriend"));
        }
        List<User> userList = this.messageService.selectUserByEnteUuid(user.getUserEnteUuid(), false, user.getUserUuid());
        List<UserVO> voList = new ArrayList<UserVO>();
        if (!CollectionUtils.isEmpty(userList)) {
            for (User item : userList) {
                UserVO vo = new UserVO();
                BeanUtil.copyProperties(item, vo);
                voList.add(vo);
            }
        }
        channel.writeAndFlush(ResponseStompFactory.createOk(voList, "listFriend"));
    }
}
