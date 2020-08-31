package com.lingmeng.user.mapper;

import com.lingmeng.user.pojo.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface UserMapper extends Mapper<User> {

    @Select("SELECT *  FROM lm_user WHERE user_name = #{userName} AND  password = #{password}")
    User queryUser(String userName,String password);
}
