/**
 * 该代码实现了一个基于 MyBatis 的拦截器（`DemonstrationWhiteInterceptor`），用于控制某些更新操作是否允许执行。以下是代码的主要功能和逻辑分析： 
 * --- 
 * ### **主要功能** 
 * 1. **拦截器作用**： 
 * - 通过拦截 `Executor.update()` 方法，判断是否允许执行非查询类型的 SQL 操作。 
 * - 如果满足特定条件（如演示模式开启、当前线程未标记为白名单、SQL 类型不是 SELECT），则抛出异常，禁止更新操作。 
 * 2. **演示模式控制**： 
 * - 使用系统配置 (`SystemPropUtil`) 和远程服务 (`RemoteUserService`) 来动态获取演示模式的状态。 
 * - 支持两种方式启用演示模式：通过系统属性 (`WHITE_DEMO=open`) 或远程服务返回的结果。 
 * 3. **线程上下文控制**： 
 * - 利用 `ThreadLocalContext.whiteUpdateThreadLocal` 标记当前线程是否允许更新操作，避免重复检查。 
 * 4. **异常处理**： 
 * - 当演示模式开启且不允许更新时，抛出自定义异常 `BusinessException`，并返回错误码 `DEMONSTRATION_FORBID_UPDATE`。 
 * --- 
 * ### **关键点分析** 
 * 1. **拦截器核心逻辑**： 
 * ```java 
 * if ((Boolean.TRUE.equals(demoOpen) || "open".equals(demo)) 
 * && Boolean.FALSE.equals(ThreadLocalContext.whiteUpdateThreadLocal.get()) 
 * && !SqlCommandType.SELECT.equals(sqlCommandType)) { 
 * throw new BusinessException(ErrorCode.DEMONSTRATION_FORBID_UPDATE); 
 * } 
 * ``` 
 * - 判断条件： 
 * - 演示模式是否开启（通过远程服务或系统属性）。 
 * - 当前线程是否已标记为允许更新。 
 * - 当前 SQL 类型是否为非查询类型（如 INSERT、UPDATE、DELETE）。 
 * 2. **依赖注入**： 
 * - 使用 `SpringContextUtil` 获取远程服务 `RemoteUserService`，动态获取演示模式状态。 
 * - 这种方式实现了松耦合设计，便于扩展和维护。 
 * 3. **线程安全**： 
 * - 使用 `ThreadLocal` 存储线程上下文信息（`whiteUpdateThreadLocal`），确保每个线程独立管理其更新权限。 
 * 4. **插件机制**： 
 * - 实现了 `Interceptor` 接口的三个方法： 
 * - `intercept()`：核心逻辑，拦截目标方法。 
 * - `plugin()`：包装目标对象，生成代理对象。 
 * - `setProperties()`：设置插件参数（未具体实现）。 
 * --- 
 * ### **优点** 
 * - **灵活性**：支持多种方式启用演示模式，便于根据环境动态调整。 
 * - **可扩展性**：通过 `ThreadLocal` 和远程服务，可以轻松扩展其他上下文信息或业务规则。 
 * - **清晰的职责划分**：拦截器专注于权限控制，不影响业务逻辑。 
 * --- 
 * ### **潜在问题与改进建议** 
 * 1. **性能问题**： 
 * - 每次调用都会访问远程服务或读取系统属性，可能影响性能。建议缓存演示模式的状态，减少不必要的远程调用。 
 * 2. **线程上下文丢失**： 
 * - 如果线程池复用线程，可能导致上下文信息被覆盖。需要确保线程上下文正确传递。 
 * 3. **异常处理**： 
 * - 当前仅抛出自定义异常，未提供详细的日志记录。建议增加日志记录，方便排查问题。 
 * --- 
 * ### **总结** 
 * 该代码实现了一个功能完善的 MyBatis 拦截器，用于控制演示模式下的更新操作权限。通过灵活的配置和线程上下文管理，确保了系统的安全性与可扩展性。但在实际应用中，需注意性能优化和线程安全问题。 
**/
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
