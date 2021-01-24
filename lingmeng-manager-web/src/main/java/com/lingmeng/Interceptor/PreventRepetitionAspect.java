package com.lingmeng.Interceptor;

import com.lingmeng.base.RestReturn;
import com.lingmeng.cache.JedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;


 /**
  * @Author skin
  * @Date  2021/1/6
  * @Description 防止重复提交
  **/
@Aspect
@Component
//proxyTargetClass=true则强制使用CGLIB代理，否则会根据目标类是否实现了接口，自动选择是JDK代理或者是CGLIB代理。
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class PreventRepetitionAspect {

	@Autowired
	private JedisUtil jedisUtils;

	private static final Logger logger = LoggerFactory.getLogger(PreventRepetitionAspect.class);


	private static final String PARAM_TOKEN = "token";
    private static final String PARAM_TOKEN_FLAG = "tokenFlag";
    

	@Around(value = "@annotation(com.lingmeng.anotation.PreventRepetitionAnnotation)")
	public Object excute(ProceedingJoinPoint joinPoint) throws Throwable{
		try {
			Object result = null;
			//获取带参方法的参数
			Object[] args = joinPoint.getArgs();
			for(int i = 0;i < args.length;i++){
				if(args[i] != null && args[i] instanceof HttpServletRequest){
					HttpServletRequest request = (HttpServletRequest) args[i];
					HttpSession session = request.getSession();
					if(request.getMethod().equalsIgnoreCase("get")){
						//方法为get
						result = generate(joinPoint, request, session, PARAM_TOKEN_FLAG);
					}else{
						//方法为post
						result = validation(joinPoint, request, session, PARAM_TOKEN_FLAG);
					}
				}
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("执行防止重复提交功能AOP失败，原因：" + e.getMessage());
			return RestReturn.error("重复提交");
		}
	}
	
	public Object generate(ProceedingJoinPoint joinPoint, HttpServletRequest request, HttpSession session, String tokenFlag) throws Throwable {
        String uuid = UUID.randomUUID().toString();
        request.setAttribute(PARAM_TOKEN, uuid);
        return joinPoint.proceed();
    } 
	
	public Object validation(ProceedingJoinPoint joinPoint, HttpServletRequest request, HttpSession session, String tokenFlag) throws Throwable {
		String requestFlag = request.getParameter(PARAM_TOKEN);
        if(StringUtils.isEmpty(requestFlag)){
			requestFlag=request.getHeader("Authorization");
		}

		//redis加锁(针对Authorization(某一个具体的人)加锁)
		boolean lock = jedisUtils.tryGetDistributedLock(tokenFlag + requestFlag, requestFlag, 6000);
		System.out.println("lock:" + lock + "," + Thread.currentThread().getName());
		if(lock){
			//加锁成功
			//执行方法
			Object funcResult = joinPoint.proceed();
			//方法执行完之后进行解锁
			jedisUtils.releaseDistributedLock(tokenFlag + requestFlag, requestFlag);
			return funcResult;
		}else{
			//锁已存在
			return RestReturn.error("不能重复提交！");
		}
    }
	
}
