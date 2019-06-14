package com.zhkj.nettyserver.message.controller;///**
// * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
// * This software is the confidential and proprietary information of
// * ZHONGHENG, Inc. You shall not disclose such Confidential
// * Information and shall use it only in accordance with the terms of the
// * license agreement you entered into with ZHONGHENG.
// */
 import com.zhkj.nettyserver.message.domain.*;
 import com.zhkj.nettyserver.message.domain.request.*;
 import com.zhkj.nettyserver.message.domain.respone.*;
 import com.zhkj.nettyserver.message.service.MessageService;
 import com.zhkj.nettyserver.util.BeanUtil;
 import com.zhkj.nettyserver.util.token.TokenUtil;
 import com.zhkj.nettyserver.util.token.TokenVO;
 import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
 import org.apache.ibatis.annotations.Lang;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Component;
 import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
 import sun.applet.Main;

 import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Des: 会话控制器
 * ClassName: ChatController
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 10:56
 */
@Component("chatController")
public class ChatController {

    @Autowired
    private MessageService messageService;

    /**
     * 创建一个会话
     *
     * @param params
     */
    @ApiOperation(value = "创建一个会话", notes = "", response = OpenVO.class)
//    @RequestMapping(value = "/open", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public OpenVO openChat(OpenParams params) {
        if (params.getChatType() == 0) {//系统会话
            Chat tmp = new Chat();
            tmp.setChatCsuseUuid(params.getsSuseUuid());
            tmp.setChatType(0);
            Chat chat = this.messageService.selectChatOne(tmp);
            //如果会话不存在创建
            if (chat == null) {
                chat = this.messageService.insertChat(params);
            }
            OpenVO vo = new OpenVO();
            BeanUtil.copyProperties(chat, vo);
            return vo;
        } else if (params.getChatType() == 1) {//一对一
            Chat chat = this.messageService.selectChatOneToOne(params.getsSuseUuid(), params.geteSuseUuid());
            if (chat == null) {//对方创建
                chat = this.messageService.selectChatOneToOne(params.geteSuseUuid(), params.getsSuseUuid());
            }
            if (chat == null) {//如果会话不存在创建
                chat = this.messageService.insertChat(params);
            }
            OpenVO vo = new OpenVO();
            BeanUtil.copyProperties(chat, vo);
            return vo;
        } else if (params.getChatType() == 2) {
            if (params.getCgroUuid() == null) {
                return null;
            }
            ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(params.getCgroUuid());
            if (chatGroup == null) {
                return null;
            }
            Chat chat =null;
            if (chatGroup.getCgroChatUuid() == null){
                chat = this.messageService.insertChat(params);
                chatGroup.setCgroChatUuid(chat.getChatUuid());
                this.messageService.updateChatGroup(chatGroup);
            }else{
                Chat tmpParams = new Chat();
                tmpParams.setChatUuid(chatGroup.getCgroChatUuid());
                chat = this.messageService.selectChatOne(tmpParams);
            }
            if (chat == null){
                return null;
            }
            OpenVO vo = new OpenVO();
            BeanUtil.copyProperties(chat, vo);
            return vo;
        } else {
            return null;
        }
    }


    /**
     * 获取最近会话
     *
     * @param suseUuid
     */
    @ApiOperation(value = "获取最近聊天的会话（没有分页）", notes = "", response = ChatVO.class)
//    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public  List<ChatVO> list(Long suseUuid) {
        boolean isAll = false;
        List<Chat> chatList = this.messageService.selectChatBySuseUuid(suseUuid, isAll);
        List<ChatVO> voList = new ArrayList<ChatVO>();
        if (!CollectionUtils.isEmpty(chatList)) {
            Message message = null;
            for (Chat chat : chatList) {
                ChatVO vo = new ChatVO();
                BeanUtil.copyProperties(chat, vo);
                message = this.messageService.selectLastMessage(chat.getChatUuid());
                if (message != null) {//如果有最后一条消息
                    ChatVO.MessageVO msgVO = new ChatVO.MessageVO();
                    BeanUtil.copyProperties(message, msgVO);
                    vo.setChatLastMessage(msgVO);
                }
                voList.add(vo);
            }
        }
        return voList;
    }


    /**
     * 获取好友
     *
     * @param suseUuid
     */
    @ApiOperation(value = "获取好友（没有分页）", notes = "", response = UserVO.class)
//    @RequestMapping(value = "/list/friend", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public List<UserVO> listFriend(Long suseUuid) {
        User user = this.messageService.selectBySuseUuid(suseUuid, false);
        if (user == null) {
            return null;
        }
        List<User> userList = this.messageService.selectUserByEnteUuid(user.getUserEnteUuid(), false,suseUuid);
        List<UserVO> voList = new ArrayList<UserVO>();
        if (!CollectionUtils.isEmpty(userList)) {
            for (User item : userList) {
                UserVO vo = new UserVO();
                BeanUtil.copyProperties(item, vo);
                voList.add(vo);
            }
        }
        return voList;
    }

    /**
     * 创建一个群
     *
     * @param groupParams
     */
    @ApiOperation(value = "创建一个群", notes = "", response = OpenVO.class)
//    @RequestMapping(value = "/open/group", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public OpenVO openGroup(OpenGroupParams groupParams) {
        OpenParams params = new OpenParams();
        BeanUtil.copyProperties(groupParams, params);
        params.setChatType(2);
        Chat chat = this.messageService.insertChat(params);
        OpenVO vo = new OpenVO();
        BeanUtil.copyProperties(chat, vo);
        return vo;
    }

    /**
     * 创建一个群
     *
     * @param params2
     */
    @ApiOperation(value = "创建一个群", notes = "", response = OpenGroupVO.class)
//    @RequestMapping(value = "/open/group2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public OpenGroupVO openGroup2(OpenGroupParams2 params2) {
        ChatGroup chatGroup = this.messageService.insertGroup(params2);
        OpenGroupVO vo = new OpenGroupVO();
        BeanUtil.copyProperties(chatGroup, vo);
        return vo;
    }

    /**
     * 获取会话
     *
     * @param
     */
    @ApiOperation(value = "获取群（没有分页）", notes = "", response = ChatVO.class)
//    @RequestMapping(value = "/list/group", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public List<ChatVO> listGroup(Long suseUuid) {
        List<Chat> chatList = this.messageService.selectGroup(suseUuid);
        List<ChatVO> voList = new ArrayList<ChatVO>();
        if (!CollectionUtils.isEmpty(chatList)) {
            for (Chat chat : chatList) {
                ChatVO vo = new ChatVO();
                BeanUtil.copyProperties(chat, vo);
                voList.add(vo);
            }
        }
        return voList;
    }

    /**
     * 获取群
     *
     * @param suseUuid
     */
    @ApiOperation(value = "获取群（没有分页）", notes = "", response = ChatGroupVO.class)
//    @RequestMapping(value = "/list/group2", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public List<ChatGroupVO> listGroup2(Long suseUuid) {
        List<ChatGroup> cGroupList = this.messageService.selectChatGroup(suseUuid);
        List<ChatGroupVO> voList = new ArrayList<ChatGroupVO>();
        if (!CollectionUtils.isEmpty(cGroupList)) {
            User user = null;
            for (ChatGroup chat : cGroupList) {
                ChatGroupVO vo = new ChatGroupVO();
                BeanUtil.copyProperties(chat, vo);
                user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
                vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
                voList.add(vo);
            }
        }
        return voList;
    }

    /**
     * 退出群
     *
     * @param params
     */
    @ApiOperation(value = "", notes = "退出群")
//    @RequestMapping(value = "/out/group", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public String outGroup(OutGroupParams params) {
        /**
         * 本身才能退出
         * 群人数需要减少
         * 退出会话
         * 会话总人数减少
         */
       if (0<this.messageService.outGroup(params)){
           return "已退出该群";
       }
      return "退出失败，未知错误";
    }

    /**
     * 增加群成员
     *
     * @param params
     */
    @ApiOperation(value = "", notes = "增加群成员")
//    @RequestMapping(value = "/add/group", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public String addGroup(AddChatGroupUserParams params) {
        /**
         * 增加人
         * 群和增加人在同一个圈里面
         * 被增加人必须激活
         * 群人数需要增加
         * 增加会话人
         * 会话人数需要增加
         */

      if (0<this.messageService.addChatGroupUser(params)) {
          return "新增成功";
      }return "新增失败";
    }

//    /**
//     * 删除群成员
//     *
//     * @param reqOb
//     */
//    @ApiOperation(value = "", notes = "删除群成员")
//    @RequestMapping(value = "/del/group", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public Response delGroup(@RequestBody @Valid Request<DelChatGroupUserParams> reqOb) {
//        DelChatGroupUserParams params = reqOb.getParams();
////        通过主键删除群成员
//        if(this.messageService.deleteChatGroupUserByCgusUuid(params) == 0) {
//           return ResponseFactory.createBad("删除失败");
//        }else {
//            return ResponseFactory.createOk("删除成功");
//        }
//    }

    /**
     * 修改群
     *
     * @param params
     */
    @ApiOperation(value = "", notes = "修改群")
//    @RequestMapping(value = "/edit/group", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public String editGroup(EditChatGroupParams params,Long suseUuid) {

        int updateCount = this.messageService.updateChatGroup(params,suseUuid.toString());
        if (updateCount == 1) {
            return "修改群成功";
        } else {
            return"修改群失败";
        }
    }

    /**
     * 修改群名片
     *
     * @param params
     */
    @ApiOperation(value = "", notes = "修改群名片")
//    @RequestMapping(value = "/edit/group/user", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public String editGroupUser(EditChatGroupUserParams params) {
        if(this.messageService.updateChatGroupUserByCgusUuid(params) <1 ){
            return"修改群名片失败";
        }else {
            return "修改群名片成功";
        }
    }

    /**
     * 获取会话消息列表
     *
     * @param params
     */
    @ApiOperation(value = "获取聊天记录", notes = "")
//    @RequestMapping(value = "/list/msg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public List<SearchMessageVO> listMsg(SearchMessageParams params) {
        List<Message> messageList = this.messageService.selectMessage(params);
        List<SearchMessageVO> voList = new ArrayList<SearchMessageVO>();
        if (!CollectionUtils.isEmpty(messageList)) {
            List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(params.getMessChatUuid());
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
        return voList;
    }


//    /**
//     * 测试页面
//     */
//    @ApiOperation(value = "测试页面", notes = "")
//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public String test() {
//        return "test";
//    }
}
