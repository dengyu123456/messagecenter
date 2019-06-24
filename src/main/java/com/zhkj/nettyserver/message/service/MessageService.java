/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.service;

import com.zhkj.nettyserver.message.domain.*;
import com.zhkj.nettyserver.message.domain.request.*;

import java.util.Date;
import java.util.List;

/**
 * Des:
 * ClassName: MessageService
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 1:33
 */
public interface MessageService {
    /**
     * 回去会话中成员
     *
     * @param messChatUuid
     * @return
     */
    List<ChatUser> selectChatUserByChatUuid(Long messChatUuid);

    /**
     * 获取会话
     *
     * @param tmp
     * @return
     */
    Chat selectChatOne(Chat tmp);

    /**
     * 创建会话
     *
     * @param params
     * @return
     */
    Chat insertChat(OpenParams params);

    /**
     * 获取用户
     *
     * @param suseUuid
     * @return
     */
    User selectBySuseUuid(Long suseUuid, boolean isAll);

    /**
     * 获取一对一的会话
     *
     * @param sSuseUuid
     * @param eSuseUuid
     * @return
     */
    Chat selectChatOneToOne(Long sSuseUuid, Long eSuseUuid);

    /**
     * 添加会话消息
     *
     * @param params
     * @return
     */
    int insertMessage(MessageParams params, Long currTime);

    /**
     * 获取会话用户
     *
     * @param messChatUuid
     * @param messSuseUuid
     * @return
     */
    ChatUser selectChatUserByChatUuidAndSuseUuid(Long messChatUuid, Long messSuseUuid);

    /**
     * 获取消息列表
     *
     * @param params
     * @return
     */
    List<Message> selectMessage(SearchMessageParams params);

    /**
     * 获取用户参与的会话
     *
     * @param suseUuid
     * @param isAll
     * @return
     */
    List<Chat> selectChatBySuseUuid(Long suseUuid, boolean isAll);

    /**
     * 根据企业/公司Uuid获取用户列表
     *
     * @param userEnteUuid
     * @return
     */
    List<User> selectUserByEnteUuid(Long userEnteUuid, boolean isAll,Long suseUuid);

    /**
     * 获取群
     *
     * @param suseUuid
     * @return
     */
    List<Chat> selectGroup(Long suseUuid);

    /**
     * 更新在线状态
     *
     * @param aLong
     * @param online
     */
    int updateChatUserOnline(Long aLong, Integer online);

    /**
     * 添加消息更新chat
     *
     * @param params
     * @param currTime
     * @return
     */
    int insertMessageAndUpdateChat(MessageParams params, Date currTime);

    /**
     * 获取回话最后一条消息
     *
     * @param chatUuid
     * @return
     */
    Message selectLastMessage(Long chatUuid);

    /**
     * 创建群
     *
     * @param params2
     * @return
     */
    ChatGroup insertGroup(OpenGroupParams2 params2);

    /**
     * 添加群用户
     *
     * @param user0
     * @return
     */
    int insertChatGroupUser(ChatGroupUser user0);

    /**
     * 添加群
     *
     * @param group
     * @return
     */
    int insertChatGroup(ChatGroup group);

    /**
     * 获取群
     *
     * @param suseUuid
     * @return
     */
    List<ChatGroup> selectChatGroup(Long suseUuid);

    /**
     * 获取群
     *
     * @param cgroUuid
     * @return
     */
    ChatGroup selectChatGroupByCgroUuid(Long cgroUuid);

    /**
     * 获取群成员
     *
     * @param cgroUuid
     * @return
     */
    List<ChatGroupUser> selectChatGroupUser(Long cgroUuid);

    /**
     * 查询群成员是否存在
     *
     * @param cgusSuseUuid
     * @return
     */
    ChatGroupUser selectChatGroupUserByCgusSuseUuidUuidAndCgusCgroUuid(Long cgusSuseUuid, Long cgusCgroUuid);

    /**
     * 退出群
     *
     * @param params
     * @return
     */
    Long outGroup(OutGroupParams params);

    /**
     * 修改群信息
     *
     * @param editChatGroupParams
     * @return
     */
    int EditChatGroup(EditChatGroupParams editChatGroupParams);

    /**
     * 增加群成员
     *
     * @param params
     * @return
     */
    int addChatGroupUser(AddChatGroupUserParams params);

    /**
     * 更新chatgroup
     *
     * @param chatGroup
     */
    void updateChatGroup(ChatGroup chatGroup);

    /**
     * 通过cgus_uuid删除群成员
     *
     * @param params
     * @return
     */
    int[] deleteChatGroupUserByCgusUuid(DelChatGroupUserParams params);

    /**
     * 修改群名片
     *
     * @param params
     */
    int updateChatGroupUserByCgusUuid(EditChatGroupUserParams params);

    /**
     * 修改群信息
     *
     * @param editChatGroupParams
     * @param uuid
     * @return
     */
    int updateChatGroup(EditChatGroupParams editChatGroupParams, String uuid);

    /**
     * 通过UserUuid查询用户
     *
     * @param sSuseUuid
     * @return
     */
    User selectUserByUserUuid(Long sSuseUuid);

    /**
     * 查询原成员
     * @param cgroUuid
     * @param userArr
     * @return
     */
   List<Long> selectOldGroupUser(Long cgroUuid,Long []userArr);

}
