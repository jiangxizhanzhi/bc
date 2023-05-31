package com.cyberpeace.config.db;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.situation.dao.entity.SituationDbAccess;
import com.cyberpeace.situation.dao.mapper.SituationDbAccessMapper;

@Intercepts({ @Signature(type = Executor.class, method = "update",
		args = { MappedStatement.class, Object.class }) })
public class LogInterceptor implements Interceptor {

	private static SituationDbAccessMapper situationDbAccessMapper;

	private static final String LOG_TABLE_NAME = SituationDbAccess.class
			.getAnnotation(TableName.class).value();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		// 执行原始方法
		Object result = invocation.proceed();

		// 记录SQL执行记录
		try {
			if (situationDbAccessMapper == null) {
				situationDbAccessMapper = SpringContextUtil.getBean("situationDbAccessMapper");
			}
			insertSqlLog(invocation, result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void insertSqlLog(Invocation invocation, Object result) {

		// 如果环境变量中未配置记录，则不记录SQL日志
		if (!"true".equals(SystemPropUtil.getProp("BC_DB_RECORD"))) {
			return;
		}

		// 获取基本信息
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		Object parameter = args[1];
		Class<?> entityClass = ms.getParameterMap().getType();

		// 获取执行的SQL语句模板
		BoundSql boundSql = ms.getBoundSql(parameter);
		String sql = boundSql.getSql();

		// 提取表名
		String tableName = getTableNameFromEntity(entityClass);
		if (tableName == null) {
			tableName = getTableNameFromSql(sql);
		}

		// 如果是对日志操作记录的SQL语句，则不再处理
		if ((sql != null && sql.contains(LOG_TABLE_NAME)) || LOG_TABLE_NAME.equals(tableName)) {
			return;
		}

		// 获取参数值
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		TypeHandlerRegistry typeHandlerRegistry = ms.getConfiguration().getTypeHandlerRegistry();
		List<Object> parameterValues = new ArrayList<>();
		for (ParameterMapping parameterMapping : parameterMappings) {
			String propertyName = parameterMapping.getProperty();
			Object propertyValue = null;
			if (boundSql.hasAdditionalParameter(propertyName)) {
				propertyValue = boundSql.getAdditionalParameter(propertyName);
			} else if (parameterObject == null) {
				propertyValue = "";
			} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				propertyValue = parameterObject;
			} else {
				MetaObject metaObject = ms.getConfiguration().newMetaObject(parameterObject);
				propertyValue = metaObject.getValue(propertyName);
			}

			// 将参数值处理后添加到参数值列表中
			parameterValues.add(getParameterValue(propertyValue));
		}

		// 将参数值拼入SQL语句中
		String actualSql = String.format(sql.replaceAll("\\?", "%s"), parameterValues.toArray());

		// 组装SQL执行记录
		SituationDbAccess situationDbAccess = new SituationDbAccess();
		situationDbAccess
				.setOperationTime(DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		situationDbAccess.setDbName(getDbName());
		situationDbAccess.setTableName(tableName);
		situationDbAccess.setOperationType(ms.getSqlCommandType().name());
		situationDbAccess.setOperationDetail(JsonUtil.toJsonString(actualSql));
		situationDbAccess.setCreateUserId(getUser());

		// 记录数据
		situationDbAccessMapper.insert(situationDbAccess);
	}

	/**
	 * 得到数据库名称
	 *
	 * @return {@code String}
	 */
	private static String getDbName() {
		String dbName = SystemPropUtil.getProp("BC_DB_NAME");
		return dbName == null ? "bc" : dbName;
	}

	/**
	 * 处理参数值为字符串
	 *
	 * @param obj 原始参数
	 * @return {@code String}
	 */
	private static String getParameterValue(Object obj) {
		{
			String value = null;
			if (obj instanceof String) {
				value = "'" + obj + "'";
			} else if (obj instanceof Date) {
				DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
						DateFormat.DEFAULT, Locale.CHINA);
				value = "'" + formatter.format(new Date()) + "'";
			} else {
				value = obj.toString();
			}
			return value;
		}
	}

	/**
	 * 获取当前用户ID
	 *
	 * @return {@code String}
	 */
	private static String getUser() {

		// 获取当前用户
		if (UserInfoContext.getUserContext() != null) {
			return UserInfoContext.getUserContext().getUserId();
		}

		// 如果当前用户为空，则返回null
		return null;
	}

	/**
	 * 通过实体得到表名称
	 *
	 * @param entityClass 实体类
	 * @return {@code String}
	 */
	private static String getTableNameFromEntity(Class<?> entityClass) {
		TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
		if (tableInfo != null) {
			return tableInfo.getTableName();
		} else {
			return null;
		}
	}

	/**
	 * 从sql提取表名
	 *
	 * @param sql sql
	 * @return {@code String}
	 */
	private static String getTableNameFromSql(String sql) {
		String tableName = null;
		int fromIndex = sql.toLowerCase().indexOf("from");
		if (fromIndex != -1) {
			int spaceIndex = sql.indexOf(" ", fromIndex + 5);
			if (spaceIndex != -1) {
				tableName = sql.substring(fromIndex + 5, spaceIndex);
			} else {
				tableName = sql.substring(fromIndex + 5);
			}
		}
		return tableName;
	}

}
