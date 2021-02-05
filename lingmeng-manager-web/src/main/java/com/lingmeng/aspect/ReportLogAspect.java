package com.lingmeng.aspect;

import com.lingmeng.anotation.Report;
import com.lingmeng.api.log.ISysReportLogService;
import com.lingmeng.common.utils.thread.CrawlerThreadPool;
import com.lingmeng.common.utils.user.UserUtil;
import com.lingmeng.enums.ResultTypeEnum;
import com.lingmeng.log.model.SysReportLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

 /**
  * @Author skin
  * @Date  2021/1/10
  * @Description 报表注解前面执行类
  **/
@Aspect
@Component
public class ReportLogAspect {



    @Autowired
    private ISysReportLogService sysReportLogService;


    @Pointcut("@annotation(com.lingmeng.anotation.Report)")
    public void getPointCut() {
    }



    @Around("getPointCut()")
    public Object around(final ProceedingJoinPoint joinPoint) throws Throwable {

        final Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //获取注解
        final Report report = method.getAnnotation(Report.class);
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        if (report == null) {
//            logger.warn("当前接口未注解@Report");
//        } else if (request == null) {
//            logger.error("未能获取到当前的request");
//        } else {
            final SysReportLog log = new SysReportLog();
            log.setName(report.value());
            log.setRequestUrl(request.getRequestURI());
//            log.setRequestIpAddress(((ShiroHttpServletRequest) request).getRequest().getRemoteAddr());
            String  userId  = (UserUtil.getUserId(request));
            log.setOperateUserId(userId);
            final Date currentDateTime = new Date();
            log.setOperateTime(currentDateTime);
            //获取注解类型字段
            log.setOperateType(report.type());

            final String getParams = request.getQueryString();
            final String postParams = HttpHelper.getBodyString(request);

            log.setGetParams(getParams);
            log.setPostParams(postParams);
            Object result = null;
            Exception exc = null;
            //执行切点
            try {
                result = joinPoint.proceed();
                log.setResultType(ResultTypeEnum.SUCCESS);
            } catch (final Exception e) {
                log.setResultType(ResultTypeEnum.FAIL);
                exc = e;
            }
            log.setDurationTimestamp(System.currentTimeMillis() - currentDateTime.getTime());

            CrawlerThreadPool.getInstance().exec(() -> {
                sysReportLogService.save(log);
            });

            if(exc != null){
                throw exc;
            }
            return result;
//        }
//        return joinPoint.proceed();
    }
}