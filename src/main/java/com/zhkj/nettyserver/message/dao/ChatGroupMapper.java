package com.zhkj.nettyserver.message.dao;


import com.zhkj.nettyserver.message.domain.ChatGroup;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ChatGroupMapper extends Mapper<ChatGroup> {
    List<ChatGroup> selectChatGroupBySuseUuid(@Param("suseUuid") Long suseUuid);

    int updateSubCountByCgroUuid(@Param("cgusCgroUuid") Long cgusCgroUuid);

    int updateAddCountByCgroUuid(@Param("cgusCgroUuid") Long cgusCgroUuid);

    int updateChatGroupCountByChatGroupUuid(@Param("cgroUuid") Long cgroUuid, @Param("length") int length);

    ChatGroup selectOneByCgroUuid(@Param("cgroUuid") Long cgroUuid);
}