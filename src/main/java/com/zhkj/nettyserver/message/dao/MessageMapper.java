package com.zhkj.nettyserver.message.dao;

import com.zhkj.nettyserver.message.domain.Message;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface MessageMapper extends Mapper<Message> {
    /**
     * 获取最后一条消息
     * @param chatUuid
     * @return
     */
    Message selectLastMessage(@Param("chatUuid") Long chatUuid);
}