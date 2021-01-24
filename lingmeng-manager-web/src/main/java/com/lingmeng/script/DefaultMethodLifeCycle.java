package com.lingmeng.script;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingmeng.dao.log.SysExecutedMethodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

 /**
  * @Author skin
  * @Date  2021/1/10
  * @Description 生命周末默认实现
  **/
 @Slf4j
public class DefaultMethodLifeCycle implements MethodLifeCycle{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final String time = sdf.format(new Date());


    @Override
    public SysExecutedMethods waiting(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods) {
        try {
            //methods指的是一个方法
            if(methods != null){

                //数据库查找当天该(method)已经执行的数据
                final List<SysExecutedMethods> sysExecutedMethods = sysExecutedMethodsMapper.selectList(new QueryWrapper<SysExecutedMethods>().lambda().eq(SysExecutedMethods::getMethodName, methods.getMethodName()).eq(SysExecutedMethods::getMethodTime, time).orderByDesc(SysExecutedMethods::getCreatedTime));
                if(CollectionUtils.isNotEmpty(sysExecutedMethods)){
                    //数据库中只有一条
                    methods = sysExecutedMethods.get(0);
                    methods.setStatus(LifeCycleEnum.WAITING);
                    sysExecutedMethodsMapper.updateById(methods);
                }else{
                    methods.setStatus(LifeCycleEnum.WAITING);
                    sysExecutedMethodsMapper.insert(methods);
                }
                return methods;
            }
        } catch (Exception e) {
            log.error("待执行异常",e);
        }
        return null;
    }


    @Override
    public void invoking(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods) {
        try {
            if(methods != null && !"".equals(methods.getId())){
                methods.setStatus(LifeCycleEnum.INVOKING);
                sysExecutedMethodsMapper.updateById(methods);
            }
        } catch (Exception e) {
            log.error("执行中异常",e);
        }
    }

    @Override
    public void exception(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods) {
        try {
            if(methods != null && !"".equals(methods.getId())){
                methods.setStatus(LifeCycleEnum.EXCEPTION);
                sysExecutedMethodsMapper.updateById(methods);
            }
        } catch (Exception e) {
            log.error("执行异常出现异常",e);
        }
    }

    @Override
    public void over(SysExecutedMethodsMapper sysExecutedMethodsMapper, SysExecutedMethods methods) {
        try {
            if(methods != null && !"".equals(methods.getId())){
                methods.setStatus(LifeCycleEnum.OVER);
                sysExecutedMethodsMapper.updateById(methods);
            }
        } catch (Exception e) {
            log.error("执行完成状态切换出现异常",e);
        }
    }
}
