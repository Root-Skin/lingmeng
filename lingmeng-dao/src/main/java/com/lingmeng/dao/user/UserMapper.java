package com.lingmeng.dao.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.user.model.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;



@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT *  FROM lm_user WHERE user_name = #{userName} AND  password = #{password}")
    User queryUser(String userName, String password);
}
