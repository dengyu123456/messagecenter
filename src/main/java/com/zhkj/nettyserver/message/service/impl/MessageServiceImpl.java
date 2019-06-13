/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.service.impl;

import com.zhkj.nettyserver.message.dao.*;
import com.zhkj.nettyserver.message.domain.*;
import com.zhkj.nettyserver.message.domain.request.*;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Des:
 * ClassName: MessageServiceImpl
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 1:33
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ChatUserMapper chatUserMapper;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ChatGroupMapper chatGroupMapper;
    @Autowired
    private ChatGroupUserMapper chatGroupUserMapper;

    /**
     * 获取会话中成员
     *
     * @param messChatUuid
     * @return
     */
    @Override
    public List<ChatUser> selectChatUserByChatUuid(Long messChatUuid) {
        return this.chatUserMapper.selectByExample(Example.builder(ChatUser.class).andWhere(Sqls.custom().andEqualTo
                ("cuseChatUuid", messChatUuid)).build());
    }

    /**
     * 获取会话
     *
     * @param tmp
     * @return
     */
    @Override
    public Chat selectChatOne(Chat tmp) {
        return this.chatMapper.selectOne(tmp);
    }

    /**
     * 创建会话
     *
     * @param params
     * @return
     */
    @Transactional
    @Override
    public Chat insertChat(OpenParams params) {
        List<ChatGroupUser> cgusList = new ArrayList<ChatGroupUser>();

        Chat chat = new Chat();
        Long chatUuid = UuidUtil.gen();
        chat.setChatUuid(chatUuid);
        chat.setChatName(params.getChatName());
        chat.setChatCsuseUuid(params.getsSuseUuid());
        chat.setChatPublic(1);
        chat.setChatType(params.getChatType());
        if (chat.getChatType() == 0 || chat.getChatType() == 1) {
//            添加一对一会话参与者UUID
            chat.setChatEsuseUuid(params.geteSuseUuid());
            ChatGroupUser user0 = new ChatGroupUser();
            user0.setCgusSuseUuid(params.getsSuseUuid());
            cgusList.add(user0);
            ChatGroupUser user1 = new ChatGroupUser();
            user1.setCgusSuseUuid(params.geteSuseUuid());
            cgusList.add(user1);
            chat.setChatCount(2);
            User userTmp = this.selectBySuseUuid(params.getsSuseUuid(), false);
            if (userTmp == null) {
                throw new RuntimeException("创建会话失败，群成员不存在");
            }
            chat.setChatName(userTmp.getUserName());

        } else if (chat.getChatType() == 2) {
            cgusList = this.selectChatGroupUser(params.getCgroUuid());
            if (CollectionUtils.isEmpty(cgusList)) {
                throw new RuntimeException("创建会话失败，群成员为空");
            }
            chat.setChatCount(cgusList.size());
        }
        Date currTime = new Date(System.currentTimeMillis());
        chat.setChatLastTime(currTime);
        chat.setCreateTime(currTime);
        int index = 0;
//        ChatUser sChatUser = new ChatUser();
//        sChatUser.setCuseUuid(UuidUtil.gen());
//        sChatUser.setCuseSuseUuid(params.getsSuseUuid());
//        sChatUser.setCuseChatUuid(chatUuid);
//        sChatUser.setCuseOrder(index);
//        sChatUser.setCuseChatStatus(0);
//        sChatUser.setCuseDataStutus(0);
//        sChatUser.setCuseLine(1);
//        User user = this.selectBySuseUuid(params.getsSuseUuid(), false);
//        if (user == null) {
//            throw new RuntimeException("客户不存在");
//        }
//        sChatUser.setCuseName(user.getUserName());
//        sChatUser.setCreateTime(currTime);
//        this.chatUserMapper.insert(sChatUser);
//        index++;
        User user = this.selectBySuseUuid(params.getsSuseUuid(), false);
        for (ChatGroupUser item : cgusList) {
            ChatUser chatUser = new ChatUser();
            chatUser.setCuseUuid(UuidUtil.gen());
            chatUser.setCuseSuseUuid(item.getCgusSuseUuid());
            chatUser.setCuseChatUuid(chatUuid);
            chatUser.setCuseOrder(index);
            chatUser.setCuseChatStatus(0);
            chatUser.setCuseDataStutus(0);
            chatUser.setCuseLine(1);
            user = this.selectBySuseUuid(item.getCgusSuseUuid(), false);
            if (user == null) {
                throw new RuntimeException("客户不存在");
            }
            chatUser.setCuseName(user.getUserName());
            chatUser.setCreateTime(currTime);
            index++;
            this.chatUserMapper.insert(chatUser);
        }
        this.chatMapper.insert(chat);
        return chat;
    }

    /**
     * 获取用户
     *
     * @param suseUuid
     * @return
     */
    @Override
    public User selectBySuseUuid(Long suseUuid, boolean isAll) {
        User params = new User();
        params.setUserUuid(suseUuid);
        params.setUserType(0);
        if (!isAll) {
            params.setUserDataStatus(0);
        }
        return this.userMapper.selectOne(params);
    }

    /**
     * 获取一对一的会话
     *
     * @param sSuseUuid
     * @param eSuseUuid
     * @return
     */
    @Override
    public Chat selectChatOneToOne(Long sSuseUuid, Long eSuseUuid) {
        return this.chatMapper.selectChatOneToOne(sSuseUuid, eSuseUuid);
    }

    /**
     * 添加会话消息
     *
     * @param params
     * @return
     */
    @Override
    public int insertMessage(MessageParams params, Long currTime) {
        Message message = new Message();
        message.setMessUuid(UuidUtil.gen());
        message.setMessChatUuid(params.getMessChatUuid());
        message.setMessSuseUuid(params.getMessSuseUuid());
        message.setMessDate(currTime);
        message.setMessContentType(params.getMessContentType());
        message.setMessContent(params.getMessContent());
        return this.messageMapper.insert(message);
    }

    /**
     * 获取会话用户
     *
     * @param messChatUuid
     * @param messSuseUuid
     * @return
     */
    @Override
    public ChatUser selectChatUserByChatUuidAndSuseUuid(Long messChatUuid, Long messSuseUuid) {
        ChatUser chatUser = new ChatUser();
        chatUser.setCuseChatUuid(messChatUuid);
        chatUser.setCuseSuseUuid(messSuseUuid);
        return this.chatUserMapper.selectOne(chatUser);
    }


    /**
     * 获取消息列表
     *
     * @param params
     * @return
     */
    @Override
    public List<Message> selectMessage(SearchMessageParams params) {
        Sqls sqls = Sqls.custom();
        if (params.getMessChatUuid() != null) {
            sqls.andEqualTo("messChatUuid", params.getMessChatUuid());
        }
        if (params.getMessStartDate() != null) {
            sqls.andGreaterThanOrEqualTo("messDate", params.getMessStartDate());
        }
        if (params.getMessEndDate() != null) {
            sqls.andLessThanOrEqualTo("messDate", params.getMessEndDate());
        }
        return this.messageMapper.selectByExample(Example.builder(Message.class).andWhere(sqls).orderByAsc("messDate").build
                ());
    }

    /**
     * 获取用户参与的会话
     *
     * @param suseUuid
     * @param isAll
     * @return
     */
    @Override
    public List<Chat> selectChatBySuseUuid(Long suseUuid, boolean isAll) {
        return this.chatMapper.selectChatBySuseUuid(suseUuid, isAll, null);
    }

    /**
     * 根据企业/公司Uuid获取用户列表
     *
     * @param userEnteUuid
     * @return
     */
    @Override
    public List<User> selectUserByEnteUuid(Long userEnteUuid, boolean isAll) {
        User params = new User();
        params.setUserEnteUuid(userEnteUuid);
        params.setUserType(0);
        if (!isAll) {
            params.setUserDataStatus(0);
        }
        return this.userMapper.select(params);
    }

    /**
     * 获取群
     *
     * @param suseUuid
     * @return
     */
    @Override
    public List<Chat> selectGroup(Long suseUuid) {
        return this.chatMapper.selectChatBySuseUuid(suseUuid, false, 2);
    }

    /**
     * 更新在线状态
     *
     * @param aLong
     * @param online
     * @return
     */
    @Override
    public int updateChatUserOnline(Long aLong, Integer online) {
        ChatUser cuser = new ChatUser();
        cuser.setCuseSuseUuid(aLong);
        cuser.setCuseLine(online);
        return this.chatUserMapper.updateByExampleSelective(cuser, Example.builder(ChatUser.class).andWhere(Sqls
                .custom().andEqualTo("cuseSuseUuid", aLong)).build());
    }

    @Transactional
    @Override
    public int insertMessageAndUpdateChat(MessageParams params, Date currTime) {
        int count = this.insertMessage(params, currTime.getTime());
        Chat chat = new Chat();
        chat.setChatLastTime(currTime);
        count += this.chatMapper.updateByExampleSelective(chat, Example.builder(Chat.class).andWhere(Sqls.custom()
                .andEqualTo("chatUuid", params.getMessChatUuid())).build());
        if (count < 2) {
            throw new RuntimeException("更新错误");
        }
        return count;
    }

    /**
     * 获取最后一条消息
     *
     * @param chatUuid
     * @return
     */
    @Override
    public Message selectLastMessage(Long chatUuid) {
//        PageHelper.startPage(1,1);
//        List<Message> tmpList = this.messageMapper.selectByExample(Example.builder(Message.class).andWhere(Sqls.custom().andEqualTo("messChatUuid",chatUuid)).orderByDesc("messDate").build());
//        return CollectionUtils.isEmpty(tmpList)?null:tmpList.get(0);
        return this.messageMapper.selectLastMessage(chatUuid);
    }

    /**
     * 创建群
     *
     * @param params2
     * @return
     */
    @Transactional
    @Override
    public ChatGroup insertGroup(OpenGroupParams2 params2) {
        ChatGroup group = new ChatGroup();
        group.setCgroUuid(UuidUtil.gen());
//        group.setCgroChatUuid();
        group.setCgroName(params2.getCgorName());
        group.setCgroCsuseUuid(params2.getsSuseUuid());
        group.setCgroPublic(0);
        group.setCgroType(1);
        group.setCgroCount(params2.geteSuseUuid().size() + 1);
        group.setCgroDataStatus(0);
        Date currTime = new Date(System.currentTimeMillis());
        group.setCreateTime(currTime);
        int count = this.insertChatGroup(group);
        int index = 0;
        ChatGroupUser user0 = new ChatGroupUser();
        user0.setCgusUuid(UuidUtil.gen());
        user0.setCgusSuseUuid(params2.getsSuseUuid());
        user0.setCgusCgroUuid(group.getCgroUuid());
        user0.setCgusOrder(index);
        User userTmp = this.selectBySuseUuid(params2.getsSuseUuid(), true);
        if (userTmp == null) {
            throw new RuntimeException("添加用户不存在");
        }
        user0.setCgusName(userTmp.getUserName());
        user0.setCgusDataStutus(0);
        user0.setCreateTime(currTime);
        count += this.insertChatGroupUser(user0);

        index++;

        for (Long item : params2.geteSuseUuid()) {
            ChatGroupUser user = new ChatGroupUser();
            user.setCgusUuid(UuidUtil.gen());
            user.setCgusSuseUuid(item);
            user.setCgusCgroUuid(group.getCgroUuid());
            user.setCgusOrder(index);
            userTmp = this.selectBySuseUuid(item, true);
            if (userTmp == null) {
                throw new RuntimeException("添加用户不存在");
            }
            user.setCgusName(userTmp.getUserName());
            user.setCgusDataStutus(0);
            user.setCreateTime(currTime);

            count += this.insertChatGroupUser(user);
            index++;
        }

        if (count < 3) {
            throw new RuntimeException("创建群失败");
        }
        return group;
    }

    /**
     * 创建实现方法
     *
     * @param user0
     * @return
     */
    @Override
    public int insertChatGroupUser(ChatGroupUser user0) {
        return this.chatGroupUserMapper.insert(user0);
    }

    /**
     * 添加群
     *
     * @param group
     * @return
     */
    @Override
    public int insertChatGroup(ChatGroup group) {
        return this.chatGroupMapper.insert(group);
    }

    /**
     * 获取群
     *
     * @param suseUuid
     * @return
     */
    @Override
    public List<ChatGroup> selectChatGroup(Long suseUuid) {
        return this.chatGroupMapper.selectChatGroupBySuseUuid(suseUuid);
    }

    /**
     * 获取群
     *
     * @param cgroUuid
     * @return
     */
    @Override
    public ChatGroup selectChatGroupByCgroUuid(Long cgroUuid) {
        ChatGroup params = new ChatGroup();
        params.setCgroUuid(cgroUuid);
        params.setCgroDataStatus(0);
        return this.chatGroupMapper.selectOne(params);
    }

    /**
     * 获取群成员
     *
     * @param cgroUuid
     * @return
     */
    @Override
    public List<ChatGroupUser> selectChatGroupUser(Long cgroUuid) {
        ChatGroupUser params = new ChatGroupUser();
        params.setCgusCgroUuid(cgroUuid);
        params.setCgusDataStutus(0);
        return this.chatGroupUserMapper.select(params);
    }

    /**
     * 查询群成员
     *
     * @param cgusSuseUuid
     * @return
     */
    @Override
    public ChatGroupUser selectChatGroupUserByCgusSuseUuidUuidAndCgusCgroUuid(Long cgusSuseUuid, Long cgusCgroUuid) {
        ChatGroupUser chatGroupUser = new ChatGroupUser();
        chatGroupUser.setCgusCgroUuid(cgusCgroUuid);
        chatGroupUser.setCgusSuseUuid(cgusCgroUuid);
        this.chatGroupUserMapper.selectOne(chatGroupUser);
        return this.chatGroupUserMapper.selectOne(chatGroupUser);
    }

    /**
     * 退出群
     *
     * @param params
     * @return
     */
    @Override
    public Long outGroup(OutGroupParams params) throws RuntimeException {
        int count = 0;
        ChatGroupUser param = new ChatGroupUser();
        param.setCgusCgroUuid(params.getCgusCgroUuid());
        param.setCgusSuseUuid(params.getCgusSuseUuid());
        ChatGroupUser selectChatGroupUser = this.chatGroupUserMapper.selectOne(param);
        if (selectChatGroupUser == null) {
            throw new RuntimeException("不存在该用户");
        }
        ChatGroupUser chatGroupUser = new ChatGroupUser();
        chatGroupUser.setCgusDataStutus(1);
//        更新退群人状态
        count += this.chatGroupUserMapper.updateByExampleSelective(chatGroupUser, Example.builder(ChatGroupUser.class).andWhere(Sqls
                .custom().andEqualTo("cgusUuid", selectChatGroupUser.getCgusUuid())).build());
        //人退出群的时候减一 （人数大于0的时候执行此操作）
        count += this.chatGroupMapper.updateSubCountByCgroUuid(params.getCgusCgroUuid());

        //如果群人数为0
        ChatGroup chatGroup = this.chatGroupMapper.selectOneByExample(Example.builder(ChatGroup.class).andWhere(Sqls
                .custom().andEqualTo("cgroUuid", selectChatGroupUser.getCgusCgroUuid())).build());
        if (chatGroup.getCgroCount().equals(0)) {
//            更新群状态为删除状态
            ChatGroup chatGroup1 = new ChatGroup();
            chatGroup1.setCgroDataStatus(1);
            count += this.chatGroupMapper.updateByExampleSelective(chatGroup1, Example.builder(ChatGroup.class).andWhere(Sqls.custom().
                    andEqualTo("cgroUuid", selectChatGroupUser.getCgusCgroUuid())).build());
            throw new RuntimeException("该群已解散");
        }
//        更新会话人数（减一）
        count += this.chatMapper.updateSubCountByChatUuid(chatGroup.getCgroChatUuid());
//        更新chat_user表的用户状态为删除
        if(chatGroup.getCgroChatUuid() == null){
//            删除群用户
            this.chatUserMapper.deleteByExample(Example.builder(ChatUser.class).andWhere(Sqls.custom().
                    andEqualTo("cuseSuseUuid", params.getCgusSuseUuid()).andEqualTo("cuseChatUuid",chatGroup.getCgroChatUuid())).build());
        }else {//更新会话用户
            ChatUser chatUser = new ChatUser();
            chatUser.setCuseDataStutus(1);
            chatUser.setCuseChatStatus(1);
            count += this.chatUserMapper.updateByExampleSelective(chatUser, Example.builder(ChatUser.class).andWhere(Sqls.custom().
                    andEqualTo("cuseSuseUuid", params.getCgusSuseUuid()).andEqualTo("cuseChatUuid",chatGroup.getCgroChatUuid())).build());
        }
//        更新chat表的会话人数减一
        if (chatGroup == null) {
            throw new RuntimeException("不存存在该群");
        } else {
//            手写sql方式  会话人数减一
            this.chatMapper.updateChatCountByChatUuid(chatGroup.getCgroChatUuid());
        }
        if (count < 4) {
            throw new RuntimeException("操作失败");
        }
        return chatGroup.getCgroChatUuid();
    }

    /**
     * 修改群信息
     *
     * @param editChatGroupParams
     * @return
     */
    @Override
    @Transactional
    public int cgroUuid(EditChatGroupParams editChatGroupParams) {
//       判断是否是群主
        int count = 0;
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setCgroUuid(editChatGroupParams.getCgroUuid());
        if (this.chatGroupMapper.selectOne(chatGroup) == null) {
            throw new RuntimeException("不是该群群主");
        } else {
            this.chatGroupMapper.updateByExampleSelective(chatGroup, Example.builder(ChatGroup.class).andWhere(Sqls.custom().
                    andEqualTo("cgroUuid", editChatGroupParams.getCgroUuid())).build());
            count = 1;
        }
        return count;
    }

    /**
     * 增加群成员
     *
     * @param params
     * @return
     */
    @Transactional
    @Override
    public int addChatGroupUser(AddChatGroupUserParams params) throws RuntimeException {
//        判断增加人是否激活
        int count = 0;
        for (int i = 0; i < params.getUserUuid().length; i++) {
            User user = this.userMapper.selectOneByUserUuid(params.getUserUuid()[i]);
            if (user == null) {
                throw new RuntimeException("不存在该用户或用户已被禁用");
            }
            User user1 = this.userMapper.selectOneByUserUuid(params.getUserUuid()[i]);
            if (user1 == null) {
                throw new RuntimeException("被邀请用户账号不存在或被禁用");
            }
            //判断被邀请人是否存在该群
            ChatGroupUser param = new ChatGroupUser();
            param.setCgusSuseUuid(params.getUserUuid()[i]);
            param.setCgusCgroUuid(params.getCgusCgroUuid());
            ChatGroupUser chatGroupUser1 = this.chatGroupUserMapper.selectOne(param);
            if (chatGroupUser1 != null) {
                throw new RuntimeException("该用户已存在该群");
            }


//      增加群成员
            ChatGroupUser chatGroupUser = new ChatGroupUser();
//        会话uuid 主键
            chatGroupUser.setCgusUuid(UuidUtil.gen());
//        群员ID
            chatGroupUser.setCgusSuseUuid(params.getUserUuid()[i]);
//        数据状态
            chatGroupUser.setCgusDataStutus(0);
//            群成员姓名
            chatGroupUser.setCgusName(user1.getUserName());
//         添加顺序
            chatGroupUser.setCgusOrder(this.chatGroupUserMapper.selectCountByExample(Example.builder(ChatGroupUser.class).
                    andWhere(Sqls.custom().andEqualTo("cgusCgroUuid",params.getCgusCgroUuid())).build())+1);
//        群uuid
            chatGroupUser.setCgusCgroUuid(params.getCgusCgroUuid());
//        创建时间
            chatGroupUser.setCreateTime(new Date(System.currentTimeMillis()));

            count += this.chatGroupUserMapper.insertSelective(chatGroupUser);
            if (count == 0) {
                throw new RuntimeException("新增失败");
            }
            //更新群人数(群人数加一)
            count += this.chatGroupMapper.updateAddCountByCgroUuid(params.getCgusCgroUuid());
            //更新会话人数
            count += this.chatMapper.updateSubCountByChatUuid(params.getUserUuid()[i]);
            if (count < 2) {
                throw new RuntimeException("新增失败");
            }
        }
        return count;
    }

    /**
     * 更新chatgroup
     *
     * @param chatGroup
     */
    @Override
    public void updateChatGroup(ChatGroup chatGroup) {
        this.chatGroupMapper.updateByExampleSelective(chatGroup, Example.builder(ChatGroup.class).andWhere(Sqls.custom().andEqualTo("cgroUuid", chatGroup.getCgroUuid())).build());
    }

    /**
     * 删除群成员
     *
     * @param params
     * @return
     */
//    @Override
//    public int deleteChatGroupUserByCgusUuid(Long cgusUuid) {
//        int count = this.chatGroupUserMapper.deleteByExample(Example.builder(ChatGroupUser.class).
//                andWhere(Sqls.custom().andEqualTo("cgusUuid",cgusUuid)).build());
//        return count;
//    }
    @Override
    public int[] deleteChatGroupUserByCgusUuid(DelChatGroupUserParams params) {
        int[] sumCount = new int[params.getCgusUuid().length];
        for (int i = 0; i < params.getCgusUuid().length; i++) {
            int count = this.chatGroupUserMapper.deleteByExample(Example.builder(ChatGroupUser.class).
                    andWhere(Sqls.custom().andEqualTo("cgusSuseUuid", params.getCgusUuid()[i]).andEqualTo("cgusCgroUuid",params.getCgroUuid())).build());
            sumCount[i] = count;
//            更新最近会话
            ChatGroup chatGroup = this.chatGroupMapper.selectOneByCgroUuid(params.getCgroUuid());
            ChatUser chatUser = new ChatUser();
            chatUser.setCuseDataStutus(1);
            chatUser.setCuseDataStutus(1);
            int count1 = this.chatUserMapper.updateByExampleSelective(chatUser, Example.builder(ChatUser.class).
                    andWhere(Sqls.custom().andEqualTo("cuseSuseUuid", params.getCgusUuid()[i]).andEqualTo("cuseChatUuid",chatGroup.getCgroChatUuid())).build());
        }
//        更新群成员数
        int countUpdate = this.chatGroupMapper.updateChatGroupCountByChatGroupUuid(params.getCgroUuid(),params.getCgusUuid().length);
        return sumCount;
    }
    /**
     * 修改群名片
     *
     * @param params
     */
    @Override
    public int updateChatGroupUserByCgusUuid(EditChatGroupUserParams params) {
        ChatGroupUser chatGroupUser = new ChatGroupUser();
        chatGroupUser.setCgusName(params.getCgusName());
        int count = this.chatGroupUserMapper.updateByExampleSelective(chatGroupUser, Example.builder(ChatGroupMapper.class).
                andWhere(Sqls.custom().andEqualTo("cgusUuid", params.getCgusUuid())).build());
        //修改回话名字
        return count;
    }

    /**
     * 修改群信息
     *
     * @param editChatGroupParams
     * @param uuid
     * @return
     */
    @Override
    public int updateChatGroup(EditChatGroupParams editChatGroupParams, String uuid) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setCgroName(editChatGroupParams.getCgroName());
        chatGroup.setCgroDataStatus(editChatGroupParams.getCgroDataStatus());
        chatGroup.setCgroPublic(editChatGroupParams.getCgroPublic());
        int count = this.chatGroupMapper.updateByExampleSelective(chatGroup, Example.builder(ChatGroup.class).
                andWhere(Sqls.custom().andEqualTo("cgroUuid", editChatGroupParams.getCgroUuid())).build());
        return count;
    }


    /**
     * 通过UserUuid查询用户
     *
     * @param sSuseUuid
     * @return User
     */
    @Override
    public User selectUserByUserUuid(Long sSuseUuid) {
//        手写mysql 语句
        return this.userMapper.selectUserByUserUuid(sSuseUuid);
    }
}
