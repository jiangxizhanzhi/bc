package com.cyberpeace.config.db;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.threadlocal.ThreadLocalContext;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Properties;

/**
 * 演示模式白名单拦截器
 * 用于在演示模式下限制某些操作，以防止对系统产生实际影响
 */
@Intercepts({ @Signature(type = Executor.class, method = "update",
		args = { MappedStatement.class, Object.class }) })
public class DemonstrationWhiteInterceptor implements Interceptor {

	// 从系统属性中获取演示模式的配置
	private String demo = SystemPropUtil.getProp("WHITE_DEMO");
	// 演示模式是否开启的标志
	private Boolean demoOpen=false;

	// 当前时间戳，用于某些操作的时效性控制
	private long now= 0L;

	/**
	 * 拦截方法
	 * 在演示模式下，如果操作不是查询且未通过白名单验证，则抛出异常阻止操作
	 *
	 * @param invocation 调用信息，包含被拦截的方法和参数
	 * @return 执行结果，如果被拦截则不返回
	 * @throws Throwable 如果抛出业务异常或其他异常
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 获取MappedStatement对象，其中包含SQL语句等信息
		MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
		// 获取SQL命令类型（INSERT、UPDATE、SELECT、DELETE等）
		SqlCommandType sqlCommandType = statement.getSqlCommandType();
		// 获取远程用户服务，用于判断演示模式是否开启
		RemoteUserService remoteUserService= SpringContextUtil
				.getBeanByClass(RemoteUserService.class);
		demoOpen= remoteUserService.isDemoOpen();
		// 如果演示模式开启或系统属性配置为开启，并且操作不是查询，则抛出异常
		if ((Boolean.TRUE.equals(demoOpen)||"open".equals(demo))
				&& Boolean.FALSE.equals(ThreadLocalContext.whiteUpdateThreadLocal.get())
				&& !SqlCommandType.SELECT.equals(sqlCommandType)) {
			throw new BusinessException(ErrorCode.DEMONSTRATION_FORBID_UPDATE);
		}
		// 继续执行原定的操作
		return invocation.proceed();
	}

	/**
	 * 插件方法
	 * 用于将当前拦截器应用到目标对象上
	 *
	 * @param target 目标对象
	 * @return 包装后的目标对象，使其具备拦截功能
	 */
	@Override
	public Object plugin(Object target) {
		// 使用MyBatis提供的Plugin.wrap方法包装目标对象
		return Plugin.wrap(target, this);
	}

	/**
	 * 设置属性方法
	 * 可以在这里设置插件的属性
	 *
	 * @param properties 属性集合
	 */
	@Override
	public void setProperties(Properties properties) {
		// 可以在这里设置插件的属性
	}
}

