package com.zhkj.nettyserver.message.dao;

import com.zhkj.nettyserver.message.domain.Chat;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ChatMapper extends Mapper<Chat> {
    Chat selectChatOneToOne(@Param("sSuseUuid") Long sSuseUuid, @Param("eSuseUuid") Long eSuseUuid);

    List<Chat> selectChatBySuseUuid(@Param("suseUuid") Long suseUuid, @Param("isAll") boolean isAll, @Param("chatType") Integer chatType);

    int updateSubCountByChatUuid(@Param("cgroChatUuid") Long cgroChatUuid);

    void updateChatCountByChatUuid(@Param("chatUuid") Long cgroChatUuid);

    int updateChatCount(@Param("chatUuid") Long chatUuid,@Param("length") int length);
}