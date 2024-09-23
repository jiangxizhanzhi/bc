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

@Intercepts({ @Signature(type = Executor.class, method = "update",
		args = { MappedStatement.class, Object.class }) })
public class DemonstrationWhiteInterceptor implements Interceptor {

	private String demo = SystemPropUtil.getProp("WHITE_DEMO");
	private Boolean demoOpen=false;


	private long now= 0L;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		SqlCommandType sqlCommandType = statement.getSqlCommandType();
		RemoteUserService remoteUserService= SpringContextUtil
				.getBeanByClass(RemoteUserService.class);

		// 定时任务都允许修改
		if (Boolean.TRUE.equals(ThreadLocalContext.whiteUpdateJobThreadLocal.get())) {
			return invocation.proceed();
		}

		demoOpen= remoteUserService.isDemoOpen();
		if ((Boolean.TRUE.equals(demoOpen)||"open".equals(demo))
				&& Boolean.FALSE.equals(ThreadLocalContext.whiteUpdateThreadLocal.get())
				&& !SqlCommandType.SELECT.equals(sqlCommandType)) {
			throw new BusinessException(ErrorCode.DEMONSTRATION_FORBID_UPDATE);
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// 可以在这里设置插件的属性
	}

}
