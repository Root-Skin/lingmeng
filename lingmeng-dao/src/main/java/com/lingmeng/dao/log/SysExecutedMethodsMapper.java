package com.lingmeng.dao.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingmeng.dao.auth.enumTestReq;
import com.lingmeng.script.SysExecutedMethods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统执行方法
 */
@Repository
public interface SysExecutedMethodsMapper extends BaseMapper<SysExecutedMethods> {

    SysExecutedMethods query(@Param("req")enumTestReq req);
}
