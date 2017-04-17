package com.gangsterhyj.dao;

import com.gangsterhyj.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by gangsterhyj on 17-1-17.
 */
@Repository
@Mapper
public interface UserInfoMapper {
    @Select({
            "select username, password, nickname, registered",
            "from user where id = #{id}"
    })
    UserInfo selectById(@Param("id")int id) throws RuntimeException;
    @Select({
            "select id, username, password, nickname, registered from user",
            "where username=#{username}"
    })
    UserInfo selectByUsername(@Param("username")String username);

    @Insert({
            "insert into user(username, password, nickname, registered)",
            "values(#{username},#{password},#{nickname}, #{registered})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()",keyProperty="id",before=false,resultType=Integer.class)
    Integer insert(@Param("username")String username, @Param("password")String password, @Param("nickname")String nickname, @Param("registered")Date registered);
}
