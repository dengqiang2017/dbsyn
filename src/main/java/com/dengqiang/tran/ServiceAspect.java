package com.dengqiang.tran;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 系统服务组件Aspect切面Bean
 * @author Shenghany
 * @date 2013-5-28
 */
//声明这是一个组件
@Component
//声明这是一个切面Bean
@Aspect
public class ServiceAspect {

	private final static Log log = LogFactory.getLog(ServiceAspect.class);
	
	//配置切入点,该方法无方法体,主要为方便同类中其他方法使用此处配置的切入点
//	@Pointcut("execution(* cn.ysh.studio.spring.aop.service..*(..))")
//	public void aspect(){}
	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点
	 * 同时接受JoinPoint切入点对象,可以没有该参数
	 */
//	@Before("aspect()")
//	public void before(JoinPoint joinPoint){
//		if(log.isInfoEnabled()){
//			log.info("before " + joinPoint);
//		}
//	}
	//配置后置通知,使用在方法aspect()上注册的切入点
//	@After("aspect()")
//	public void after(JoinPoint joinPoint){
//		if(log.isInfoEnabled()){
//			log.info("after " + joinPoint);
//		}
//	}
	public static final String transactionManagerName="transactionManager";
	public static final String transactionManagerMssqlName="transactionManagerMssql";
	///////////////////
	@Resource(name=transactionManagerName)
	private DataSourceTransactionManager txManager;
	@Resource(name=transactionManagerMssqlName)
	private DataSourceTransactionManager transactionManagerMssql;
	/////////////////////
	/**
	 * 获取多数据源下的
	 * @param name
	 * @return
	 */
	private DataSourceTransactionManager getTransactionManager(String name) {
		 if("transactionManagerMssql".equals(name)){
			 return transactionManagerMssql;
		 }else{
			 return txManager;
		 }
	}
	//配置环绕通知,使用在方法aspect()上注册的切入点
//	@Around("aspect()")JoinPoint joinPoint,
	@Around(value = "execution(* com.dengqiang.service.impl..*(..)) && @annotation(tran)") 
	public Object around(ProceedingJoinPoint pjd, Transactional tran){
		long start = System.currentTimeMillis();
		Object obj=null;
		try {
			//TODO 事务管理
			DataSourceTransactionManager transactionManager = getTransactionManager(tran.name());
			TransactionStatus status=startTran(transactionManager);
			try {
				obj=pjd.proceed();
				commitTran(transactionManager, status, true);
			} catch (Throwable e) {
				commitTran(transactionManager, status, false);
				log.error("提交错误,数据回滚!");
			}
			long end = System.currentTimeMillis();
			if(log.isInfoEnabled()){
				log.info("around " + pjd + "\tUse time : " + (end - start) + " ms!");
			}
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			if(log.isInfoEnabled()){
				log.info("around " + pjd + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
			}
			e.printStackTrace();
		}
		return obj;
	}
	//配置后置返回通知,使用在方法aspect()上注册的切入点
//	@AfterReturning("aspect()")
//	public void afterReturn(JoinPoint joinPoint){
//		if(log.isInfoEnabled()){
//			log.info("afterReturn " + joinPoint);
//		}
//	}
	//配置抛出异常后通知,使用在方法aspect()上注册的切入点
//	@AfterThrowing(pointcut="aspect()", throwing="ex")
//	public void afterThrow(JoinPoint joinPoint, Exception ex){
//		if(log.isInfoEnabled()){
//			log.info("afterThrow " + joinPoint + "\t" + ex.getMessage());
//		}
//	}
	//////////////////////事务//////////
	/**
	 * 开启事务
	 * @return 事务状态
	 */
	private TransactionStatus startTran(DataSourceTransactionManager txManager) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
		TransactionStatus status = txManager.getTransaction(def); // 获得事务状态
		return status; 
	}
	/**
	 * 提交事务
	 * @param status 事务状态
	 * @param type true提交,false-回滚
	 */
	private void commitTran(DataSourceTransactionManager txManager,TransactionStatus status, boolean type) {
		if(type){
			txManager.commit(status);
		}else{
			txManager.rollback(status);
		}
	}
}