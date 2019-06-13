package com.zhkj.nettyserver.message.dao;


import com.zhkj.nettyserver.message.domain.ChatUser;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface ChatUserMapper extends Mapper<ChatUser> {
    int updateChatUserByCuseSuseUuidAndCuseChatUuid();
}