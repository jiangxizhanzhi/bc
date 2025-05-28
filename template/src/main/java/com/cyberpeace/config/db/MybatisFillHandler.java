package com.cyberpeace.config.db;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * mybatis 填充处理器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see MetaObjectHandler
 * @since 1.0.0
 */
public class MybatisFillHandler implements MetaObjectHandler {

	/**
	 * 插入填充
	 *
	 * @param metaObject Meta 对象
	 * @throws
	 * @see MetaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("createTime", new Date(), metaObject);
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}

	/**
	 * 更新填充
	 *
	 * @param metaObject Meta 对象
	 * @throws
	 * @see MetaObject
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}
}