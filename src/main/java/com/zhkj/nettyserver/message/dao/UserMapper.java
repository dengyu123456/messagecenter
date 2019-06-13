package com.zhkj.nettyserver.message.dao;

import com.zhkj.nettyserver.message.domain.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
    User selectOneByUserUuid(@Param("userUuid") Long cgusSuseUuid);

    User selectUserByUserUuid(@Param("userUuid") Long sSuseUuid);
}