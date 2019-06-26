package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.*;
import com.zhkj.nettyserver.message.domain.ChatGroup;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.request.AddChatGroupUserParams;
import com.zhkj.nettyserver.message.domain.request.DelChatGroupUserParams;
import com.zhkj.nettyserver.message.domain.request.EditUpdateChatGroupParams;
import com.zhkj.nettyserver.message.domain.respone.ChatGroupVO;
import com.zhkj.nettyserver.message.domain.respone.OutGroupVo;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Des:
 * ClassName: EditUpdateGroupUserHandler
 * Author: dengyi
 * Date: 2019-06-26 15:23
 */
public class EditUpdateGroupUserHandler extends SimpleChannelInboundHandler<EditUpdateChatGroupParams> {
    MessageService messageService = null;

    public EditUpdateGroupUserHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EditUpdateChatGroupParams eucg) throws Exception {
        editUpdateGroupUser(eucg, ctx.channel());
    }

    private void delGroup(DelChatGroupUserParams dgup, Channel channel) {
        if (dgup.getCgroUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定群号码Uuid", "delGroup"));
            return;
        }
        if (dgup.getCgusUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定群成员", "delGroup"));
            return;
        }
        ChatGroup cgro = this.messageService.selectChatGroupByCgroUuid(dgup.getCgroUuid());
        //如果不是创建者
        if (!cgro.getCgroCsuseUuid().equals(cgro.getCgroCsuseUuid())) {
            channel.writeAndFlush(ResponseStompFactory.createBad("您不是群主，无法删除群成员", "delGroup"));
        } else {

//        通过主键删除群成员
            int count[] = this.messageService.deleteChatGroupUserByCgusUuid(dgup);
            User user = null;
            if (cgro != null) {
                ChatGroup chat = cgro;
                ChatGroupVO vo = new ChatGroupVO();
                ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(dgup.getCgroUuid());
                BeanUtil.copyProperties(chatGroup, vo);
                //获取群成员的并返回
                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
                vo.setChatGroupUserList(chatGroupUserList);
                user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
                vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
                //        获取所有成员来通知删除
                List<ChatGroupUser> chatGroupUserList0 = this.messageService.selectChatGroupUser(dgup.getCgroUuid());
                for (ChatGroupUser chatGroupUser : chatGroupUserList0) {
                    Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, chatGroupUser.getCgusSuseUuid());
                    if (otherChannel != null && otherChannel.isActive()) {
                        otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "updateGroup"));
                    }
                }
            }
            //通知被删除的用户
            Long[] delUserList = dgup.getCgusUuid();
            for (Long userUuid : delUserList) {
                User delUser = this.messageService.selectUserByUserUuid(userUuid);
                OutGroupVo vo = new OutGroupVo();
                BeanUtil.copyProperties(cgro, vo);
                vo.setCgroSuseName(delUser.getUserName());
                vo.setCgusSuseUuid(userUuid);
                Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, userUuid);
                if (otherChannel != null && otherChannel.isActive()) {
                    otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "delGroup"));
                }

            }
        }
    }

    private void editUpdateGroupUser(EditUpdateChatGroupParams eucg, Channel channel) {
        /**
         * 1、拿到新的群成员list
         *   对比和之前的差异  新增的不用判断是否是群主 但是要判断是否是群员  增加到数据库
         *   删除的判断是否是群主 群人数减少  将数据库中的删除
         */
        if (eucg == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定编辑群信息", "updateGroup"));
            return;
        }
        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(eucg.getCgusCgroUuid());
        if (CollectionUtil.isEmpty(chatGroupUserList)) {
            channel.writeAndFlush(ResponseStompFactory.createBad("不存在当前群", "updateGroup"));
        }
        Long[] newGroupUserList = eucg.getUserUuid();
        Long[] oldGroupUserList = new Long[chatGroupUserList.size()];
        int i = 0;
        for (ChatGroupUser item : chatGroupUserList) {
            oldGroupUserList[i] = item.getCgusSuseUuid();
            i++;
        }
        //两个数组的交集
        Long[] J = ArrayUtil.getJ(newGroupUserList, oldGroupUserList);
        //拿到新增的 新的群成员去掉交集
        Long[] addGroupUserList = ArrayUtil.getC(newGroupUserList, J);
        if (addGroupUserList != null && addGroupUserList.length > 0) {
            AddChatGroupUserParams agup = new AddChatGroupUserParams();
            agup.setCgusCgroUuid(eucg.getCgusCgroUuid());
            agup.setUserUuid(addGroupUserList);
            agup.setCgusSuseUuid(eucg.getCgusSuseUuid());
            addGroup(agup, channel);
        }
        //拿到被删除的  原群成员去掉交集
        Long[] delGroupUserList = ArrayUtil.getC(oldGroupUserList, J);
        if (delGroupUserList != null && delGroupUserList.length > 0) {
            DelChatGroupUserParams dgup = new DelChatGroupUserParams();
            dgup.setCgroUuid(eucg.getCgusCgroUuid());
            dgup.setCgusUuid(delGroupUserList);
            delGroup(dgup, channel);
        }
    }

    private void addGroup(AddChatGroupUserParams agup, Channel channel) {
        /**
         * 增加人
         * 群和增加人在同一个圈里面
         * 被增加人必须激活
         * 群人数需要增加
         * 增加会话人
         * 会话人数需要增加
         */

        if (agup.getCgusSuseUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定群参与用户Uuid", "addGroup"));
            return;
        }
        if (agup.getUserUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定被拉人Uuid", "addGroup"));
            return;
        }
        if (agup.getCgusCgroUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定群Uuid", "addGroup"));
            return;
        }
        try {
            this.messageService.addChatGroupUser(agup);
        } catch (Exception e) {
            channel.writeAndFlush(ResponseStompFactory.createBad("新增失败,该用户已存在该群", "addGroup"));
        } finally {
            ChatGroup rCgro = this.messageService.selectChatGroupByCgroUuid(agup.getCgusCgroUuid());
            if (rCgro != null) {
                User user = null;
                ChatGroup chat = rCgro;
                ChatGroupVO vo = new ChatGroupVO();
                BeanUtil.copyProperties(chat, vo);
                //获取群成员的并返回
                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
                vo.setChatGroupUserList(chatGroupUserList);

                user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
                vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
                //发送群列表新增的
                Long[] addUserList = agup.getUserUuid();
                List<Long> oldGroupUserArr = this.messageService.selectOldGroupUser(agup.getCgusCgroUuid(), addUserList);
                for (Long userUuid : addUserList) {
                    Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, userUuid);
                    if (otherChannel != null && otherChannel.isActive()) {
                        otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "addGroup"));
                    }
                }
                //原来的  去掉新增的
                for (Long item : oldGroupUserArr) {
                    Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, item);
                    if (otherChannel != null && otherChannel.isActive()) {
                        otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "updateGroup"));
                    }
                }
            }
        }

    }
}
