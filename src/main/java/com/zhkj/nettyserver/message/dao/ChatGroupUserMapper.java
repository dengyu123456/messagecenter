package com.zhkj.nettyserver.message.dao;


import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ChatGroupUserMapper extends Mapper<ChatGroupUser> {
    ChatGroupUser selectMinGroupUser(Long cgroUuid);

    List<Long> selectOldGroupUser(@Param("cgroUuid") Long cgroUuid, @Param("cgusSuseUuid") List<Long> cgusSuseUuid);
}