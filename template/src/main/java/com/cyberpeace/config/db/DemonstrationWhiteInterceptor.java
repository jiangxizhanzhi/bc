package com.cyberpeace.config.db;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.threadlocal.ThreadLocalContext;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * 演示白色拦截器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Interceptor
 * @since 1.0.0
 */
@Intercepts({ @Signature(type = Executor.class, method = "update",
		args = { MappedStatement.class, Object.class }) })
public class DemonstrationWhiteInterceptor implements Interceptor {

	/**
	 * 演示
	 *
	 * @see String
	 */
	private String demo = SystemPropUtil.getProp("WHITE_DEMO");
	/**
	 * 演示开启
	 *
	 * @see Boolean
	 */
	private Boolean demoOpen=false;


	/**
	 * 现在
	 */
	private long now= 0L;

	/**
	 * 拦截
	 *
	 * @param invocation
	 * @return 对象
	 * @throws @throws Throwable
	 * @see Invocation
	 * @see Object
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		SqlCommandType sqlCommandType = statement.getSqlCommandType();
		RemoteUserService remoteUserService= SpringContextUtil
				.getBeanByClass(RemoteUserService.class);
		demoOpen= remoteUserService.isDemoOpen();
		if ((Boolean.TRUE.equals(demoOpen)||"open".equals(demo))
				&& Boolean.FALSE.equals(ThreadLocalContext.whiteUpdateThreadLocal.get())
				&& !SqlCommandType.SELECT.equals(sqlCommandType)) {
			throw new BusinessException(ErrorCode.DEMONSTRATION_FORBID_UPDATE);
		}
		return invocation.proceed();
	}

	/**
	 * @param target 目标
	 * @return 对象
	 * @throws
	 * @see Object
	 * @see Object
	 */
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	/**
	 * 设置性能
	 *
	 * @param properties 性能
	 * @throws
	 * @see Properties
	 */
	@Override
	public void setProperties(Properties properties) {
		// 可以在这里设置插件的属性
	}

}
