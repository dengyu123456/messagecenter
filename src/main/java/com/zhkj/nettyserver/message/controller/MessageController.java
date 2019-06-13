///**
// * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
// * This software is the confidential and proprietary information of
// * ZHONGHENG, Inc. You shall not disclose such Confidential
// * Information and shall use it only in accordance with the terms of the
// * license agreement you entered into with ZHONGHENG.
// */
//package com.zhkj.nettyserver.message.controller;
//
//
//import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
//import com.zhkj.nettyserver.message.domain.Chat;
//import com.zhkj.nettyserver.message.domain.ChatUser;
//import com.zhkj.nettyserver.message.domain.Message;
//import com.zhkj.nettyserver.message.domain.request.MessageParams;
//import com.zhkj.nettyserver.message.domain.respone.MessageVO;
//import com.zhkj.nettyserver.message.service.MessageService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.CollectionUtils;
//
//import java.security.Principal;
//import java.util.*;
//
///**
// * Des:
// * ClassName: MessageController
// * Author: biqiang2017@163.com
// * Date: 2018/11/7
// * Time: 23:43
// */
//@Controller
//public class MessageController {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//    @Autowired
//    private MessageService messageService;
//
//    @MessageMapping("/chat")
//    public void handleChat(MessageParams params) {
//        if (params.getMessChatUuid() == null) {//没有会话
////            messagingTemplate.convertAndSendToUser(String.valueOf(params.getMessSuseUuid()), "/message",
////                    ResponseStompFactory.createBad("请指定会话", "chat"));
//        } else {//有会话Uuid
//            Chat chatParams = new Chat();
//            chatParams.setChatUuid(params.getMessChatUuid());
//            Chat chat = this.messageService.selectChatOne(chatParams);
//            if (chat == null) {//没有chat
//                messagingTemplate.convertAndSendToUser(String.valueOf(params.getMessSuseUuid()), "/message",
//                        ResponseStompFactory.createBad("会话不存在", "chat"));
//            } else {
//                List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(params.getMessChatUuid());
//                Date currTime = new Date(System.currentTimeMillis());
//                if (1 < this.messageService.insertMessageAndUpdateChat(params, currTime)) {//添加成功
//                    //更新chat消息时间
//                    MessageVO vo = new MessageVO();
//                    vo.setMessStatus(Message.SUCCESS);
//                    vo.setMessChatUuid(params.getMessChatUuid());
//                    vo.setMessContent(params.getMessContent());
//                    vo.setMessContentType(params.getMessContentType());
//                    vo.setMessDate(currTime.getTime());
//                    vo.setMessSuseUuid(params.getMessSuseUuid());
//                    ChatUser sChatUser = this.messageService.selectChatUserByChatUuidAndSuseUuid(params.getMessChatUuid(),
//                            params.getMessSuseUuid());
//                    vo.setMessSuseName(sChatUser == null ? "未知" : sChatUser.getCuseName());
//                    for (ChatUser chatUser : chatUserList) {
//                        messagingTemplate.convertAndSendToUser(String.valueOf(chatUser.getCuseSuseUuid()),
//                                "/message", ResponseStompFactory.createOk(vo, "chat"));
//                    }
//                } else {
//                    messagingTemplate.convertAndSendToUser(String.valueOf(params.getMessSuseUuid()), "/message",
//                            ResponseStompFactory.createBad("消息发送失败", "chat"));
//                }
//            }
//        }
//    }
//
//    /**
//     * 创建一个会话
//     *
//     * @param reqOb
//     */
//    @MessageMapping("/chat/open")
//    public void openChat(OpenParams reqOb, Principal principal) {
//        OpenParams params = reqOb;
//        if (params.getChatType() == 0) {//系统会话
//            Chat tmp = new Chat();
//            tmp.setChatCsuseUuid(params.getsSuseUuid());
//            tmp.setChatType(0);
//            tmp.setChatName("系统通知");
//            Chat chat = this.messageService.selectChatOne(tmp);
//            if (chat == null) {//如果会话不存在创建
//                chat = this.messageService.insertChat(params);
//            }
//            OpenVO vo = new OpenVO();
//            BeanUtil.copyProperties(chat, vo);
//            this.send(principal.getName(), ResponseStompFactory.createOk(vo, "chatOpen"));
//        } else if (params.getChatType() == 1) {//一对一
//            Chat chat = this.messageService.selectChatOneToOne(params.getsSuseUuid(), params.geteSuseUuid());
//            if (chat == null) {//对方创建
//                chat = this.messageService.selectChatOneToOne(params.geteSuseUuid(), params.getsSuseUuid());
//            }
//            if (chat == null) {//如果会话不存在创建
//                chat = this.messageService.insertChat(params);
//            }
////            List<User> user = this.messageService.selectUserByEnteUuid(params.getsSuseUuid(),true);
//            User user0 = this.messageService.selectUserByUserUuid(chat.getChatCsuseUuid());
//            User user1 = this.messageService.selectUserByUserUuid(chat.getChatEsuseUuid());
//            OpenVO vo = new OpenVO();
////            设置会话会话创建者用户
//            vo.setChatCsuseName(user0 == null ? "未知" : user0.getUserName());
////            设置会话名字为对方姓名
//
//
//            BeanUtil.copyProperties(chat, vo);
//            //创建者姓名
//            if (principal.getName().equals(user0.getUserUuid().toString())) {
//                vo.setChatName(user1 == null ? "未知" : user1.getUserName());
//            } else {
//                vo.setChatName(user0 == null ? "未知" : user0.getUserName());
//            }
//            this.send(principal.getName(), ResponseStompFactory.createOk(vo, "chatOpenS"));
//            if (params.geteSuseUuid().equals(user0.getUserUuid())) {
//                vo.setChatName(user1 == null ? "未知" : user1.getUserName());
//            } else {
//                vo.setChatName(user0 == null ? "未知" : user0.getUserName());
//            }
//            this.send(params.geteSuseUuid(), ResponseStompFactory.createOk(vo, "chatOpenE"));
//        } else if (params.getChatType() == 2) {
//            if (params.getCgroUuid() == null) {
//                this.send(principal.getName(), ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
//            }
//            ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(params.getCgroUuid());
//            if (chatGroup == null) {
//                this.send(principal.getName(), ResponseStompFactory.createBad("创建会话失败,不存在该群", "chatOpen"));
//            }
//            Chat chat = null;
//            if (chatGroup.getCgroChatUuid() == null) {
//                chat = this.messageService.insertChat(params);
//                chatGroup.setCgroChatUuid(chat.getChatUuid());
//                this.messageService.updateChatGroup(chatGroup);
//            } else {
//                Chat tmpParams = new Chat();
//                tmpParams.setChatUuid(chatGroup.getCgroChatUuid());
//                chat = this.messageService.selectChatOne(tmpParams);
//            }
//            if (chat == null) {
//                this.send(principal.getName(), ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
//            }
//            OpenVO vo = new OpenVO();
//            BeanUtil.copyProperties(chat, vo);
////            this.send(principal.getName(),ResponseStompFactory.createOk(vo,"chatOpen"));
//            List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(chat.getChatUuid());
//            Long sUserUuid = Long.valueOf(principal.getName());
//            for (ChatUser item : chatUserList) {
//                this.send(item.getCuseSuseUuid(), ResponseStompFactory.createOk(vo, sUserUuid.equals(item.getCuseSuseUuid()) ? "chatOpenS" : "chatOpenE"));
//            }
//        } else {
//            this.send(principal.getName(), ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
//        }
//    }
//
//    /**
//     * 获取最近会话
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/chat/list")
//    public void chatList(Long reqOb, Principal principal) {
//        Long suseUuid = reqOb;
//        boolean isAll = false;
//        List<Chat> chatList = this.messageService.selectChatBySuseUuid(suseUuid, isAll);
//        List<ChatVO> voList = new ArrayList<ChatVO>();
//        if (!CollectionUtils.isEmpty(chatList)) {
//            Message message = null;
//            for (Chat chat : chatList) {
//                ChatVO vo = new ChatVO();
//                BeanUtil.copyProperties(chat, vo);
//                message = this.messageService.selectLastMessage(chat.getChatUuid());
//                if (message != null) {//如果有最后一条消息
//                    ChatVO.MessageVO msgVO = new ChatVO.MessageVO();
//                    BeanUtil.copyProperties(message, msgVO);
//                    vo.setChatLastMessage(msgVO);
//                }
//                voList.add(vo);
//            }
//        }
//        this.send(principal.getName(), ResponseStompFactory.createOk(voList, "chatList"));
//    }
//
//    /**
//     * 获取好友
//     *
//     * @param reqOb
//     */
//
//    @MessageMapping(value = "/list/friend")
//    public void listFriend(Long reqOb, Principal principal) {
//        Long suseUuid = reqOb;
//        User user = this.messageService.selectBySuseUuid(suseUuid, false);
//        if (user == null) {
//            this.send(principal.getName(), ResponseStompFactory.createBad("获取好友列表失败，该用户不存在", "listFriend"));
//        }
//        List<User> userList = this.messageService.selectUserByEnteUuid(user.getUserEnteUuid(), false);
//        List<UserVO> voList = new ArrayList<UserVO>();
//        if (!CollectionUtils.isEmpty(userList)) {
//            for (User item : userList) {
//                UserVO vo = new UserVO();
//                BeanUtil.copyProperties(item, vo);
//                voList.add(vo);
//            }
//        }
//        this.send(principal.getName(), ResponseStompFactory.createOk(voList, "listFriend"));
//    }
//
//    /**
//     * 创建一个群
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/open/group")
//    public void openGroup2(OpenGroupParams2 reqOb, Principal principal) {
//        OpenGroupParams2 params = reqOb;
//        ChatGroup chatGroup = this.messageService.insertGroup(params);
//        List<Long> userList = params.geteSuseUuid();
//        OpenGroupVO vo = new OpenGroupVO();
//        BeanUtil.copyProperties(chatGroup, vo);
//        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chatGroup.getCgroUuid());
//        vo.setChatGroupUserList(chatGroupUserList);
//        for (Long user : userList) {
//            this.send(user, ResponseStompFactory.createOk(vo, "openGroup2"));
//        }
//        this.send(principal.getName(), ResponseStompFactory.createOk(vo, "openGroup2"));
//
//    }
//
//    /**
//     * 获取群（添加了获取群成员的功能）
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/list/group")
//    public void listGroup(Long reqOb, Principal principal) {
//        Long suseUuid = reqOb;
//        List<ChatGroup> cGroupList = this.messageService.selectChatGroup(suseUuid);
//        List<ChatGroupVO> voList = new ArrayList<ChatGroupVO>();
//        if (!CollectionUtils.isEmpty(cGroupList)) {
//            User user = null;
//            for (ChatGroup chat : cGroupList) {
//                ChatGroupVO vo = new ChatGroupVO();
//                BeanUtil.copyProperties(chat, vo);
////                获取群成员的并返回
//                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
//                vo.setChatGroupUserList(chatGroupUserList);
//                user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
//                vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
//                voList.add(vo);
//            }
//        }
//        this.send(principal.getName(), ResponseStompFactory.createOk(voList, "listGroup"));
//    }
//
//    /**
//     * 退出群
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/out/group")
//    public void outGroup(OutGroupParams reqOb, Principal principal) {
//        /**
//         * 本身才能退出
//         * 群人数需要减少
//         * 退出会话
//         * 会话总人数减少
//         */
//        OutGroupParams params = reqOb;
//        Long chatGroupChatUuid = null;
//        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(params.getCgusCgroUuid());
//        try {
//            User user = this.messageService.selectUserByUserUuid(params.getCgusSuseUuid());
//            chatGroupChatUuid = this.messageService.outGroup(params);
//        } catch (Exception e) {
//            LOGGER.info(e.getMessage());
//            this.send(principal.getName(), ResponseStompFactory.createOk("退群失败", "outGroup"));
//        }
//        OutGroupVo vo = new OutGroupVo();
//        BeanUtil.copyProperties(params,vo);
//        vo.setGroupChatUuid(chatGroupChatUuid);
//        for (ChatGroupUser chatGroupUser : chatGroupUserList) {
//            this.send(chatGroupUser.getCgusSuseUuid(), ResponseStompFactory.createOk(params, "outGroup"));
//        }
//    }
//
//    /**
//     * 增加群成员
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/add/group")
//    public void addGroup(AddChatGroupUserParams reqOb, Principal principal) {
//        /**
//         * 增加人
//         * 群和增加人在同一个圈里面
//         * 被增加人必须激活
//         * 群人数需要增加
//         * 增加会话人
//         * 会话人数需要增加
//         */
//        AddChatGroupUserParams params = reqOb;
//        try {
//            int count = this.messageService.addChatGroupUser(params);
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage());
//            this.send(principal.getName(), ResponseStompFactory.createOk("新增失败", "addGroup"));
//        } finally {
//            ChatGroup cGroup = this.messageService.selectChatGroupByCgroUuid(params.getCgusCgroUuid());
//            List<ChatGroupVO> voList = new ArrayList<ChatGroupVO>();
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
//                voList.add(vo);
//                for (ChatGroupUser chatGroupUser : chatGroupUserList) {
//                    this.send(chatGroupUser.getCgusSuseUuid(), ResponseStompFactory.createOk(voList, "addGroup"));
//                }
//            }
//        }
////        this.send(principal.getName(), ResponseStompFactory.createOk("新增成功", "addGroup"));
//
//    }
//
//
//    /**
//     * 删除群成员
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/del/group")
//    public void delGroup(DelChatGroupUserParams reqOb, Principal principal) {
//        DelChatGroupUserParams params = reqOb;
////        获取所有成员来通知删除
//        List<ChatGroupUser> chatGroupUserList0 = this.messageService.selectChatGroupUser(params.getCgroUuid());
////        通过主键删除群成员
//        int count[] = this.messageService.deleteChatGroupUserByCgusUuid(params);
//        ChatGroup cGroup = this.messageService.selectChatGroupByCgroUuid(params.getCgroUuid());
//        List<ChatGroupVO> voList = new ArrayList<ChatGroupVO>();
//        if (cGroup != null) {
//            User user = null;
//            ChatGroup chat = cGroup;
//            ChatGroupVO vo = new ChatGroupVO();
//            BeanUtil.copyProperties(chat, vo);
////                获取群成员的并返回
//            List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
//            vo.setChatGroupUserList(chatGroupUserList);
//            user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
//            vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
//            voList.add(vo);
//            for (ChatGroupUser chatGroupUser : chatGroupUserList0) {
//                this.send(chatGroupUser.getCgusSuseUuid(), ResponseStompFactory.createOk(voList, "delGroup"));
//            }
//        }
//    }
//
//    /**
//     * 修改群
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/edit/group")
//    public void editGroup(EditChatGroupParams reqOb, Principal principal) {
//        EditChatGroupParams editChatGroupParams = reqOb;
//        this.messageService.updateChatGroup(editChatGroupParams, principal.getName());
//        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(editChatGroupParams.getCgroUuid());
//        ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(editChatGroupParams.getCgroUuid());
//        ChatGroup chat = chatGroup;
//        ChatGroupVO vo = new ChatGroupVO();
//        BeanUtil.copyProperties(chat, vo);
//        vo.setChatGroupUserList(chatGroupUserList);
//        for (ChatGroupUser chatGroupUser : chatGroupUserList) {
//            this.send(chatGroupUser.getCgusSuseUuid(), ResponseStompFactory.createOk(vo, "editGroup"));
//        }
//    }
//
//    /**
//     * 修改群名片
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/edit/group/user")
//    public void editGroupUser(EditChatGroupUserParams reqOb, Principal principal) {
//        EditChatGroupUserParams params = reqOb;
//        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(params.getCgusCgroUuid());
//        if (this.messageService.updateChatGroupUserByCgusUuid(params) < 1) {
//            this.send(principal.getName(), ResponseStompFactory.createBad("修改群名片失败", "editGroupUser"));
//        } else {
//            this.send(principal.getName(), ResponseStompFactory.createOk("修改群成员名片成功", "editGroupUser"));
//        }
//        for (ChatGroupUser chatGroupUser : chatGroupUserList) {
//            this.send(principal.getName(), ResponseStompFactory.createOk("修改群成员名片成功", "editGroupUser"));
//        }
//    }
//
//    /**
//     * 获取会话消息列表
//     *
//     * @param reqOb
//     */
//    @MessageMapping(value = "/list/msg")
//    public void listMsg(SearchMessageParams reqOb, Principal principal) {
//        SearchMessageParams params = reqOb;
//        List<Message> messageList = this.messageService.selectMessage(params);
//        List<SearchMessageVO> voList = new ArrayList<SearchMessageVO>();
//        if (!CollectionUtils.isEmpty(messageList)) {
//            List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(params.getMessChatUuid());
//            Map<Long, String> chatUserMap = new HashMap<Long, String>();
//            for (ChatUser cItem : chatUserList) {
//                chatUserMap.put(cItem.getCuseSuseUuid(), cItem.getCuseName());
//            }
//            for (Message item : messageList) {
//                SearchMessageVO vo = new SearchMessageVO();
//                BeanUtil.copyProperties(item, vo);
//                vo.setMessSuseName(chatUserMap.containsKey(vo.getMessSuseUuid()) ? chatUserMap.get(vo.getMessSuseUuid()) : "未知");
//                voList.add(vo);
//            }
//        }
//        this.send(principal.getName(), ResponseStompFactory.createOk(voList, "listMsg"));
//    }
//
//    /**
//     * 推送消息
//     *
//     * @param suseUuid
//     * @param res
//     */
//    private void send(Long suseUuid, ResponseStomp res) {
//        this.send(String.valueOf(suseUuid), res);
//    }
//
//    /**
//     * 推送消息
//     *
//     * @param suseUuid
//     * @param res
//     */
//    private void send(String suseUuid, ResponseStomp res) {
//        messagingTemplate.convertAndSendToUser(suseUuid,
//                "/message", res);
//    }
//
//    /**
//     * 指定发送消息
//     *
//     * @param resp
//     * @return
//     * @throws Exception
//     */
//    @MessageMapping("/message")
//    @SendToUser("/message")
//    public ResponseStomp userMessage(ResponseStomp resp) throws Exception {
//        return resp;
//    }
//}
