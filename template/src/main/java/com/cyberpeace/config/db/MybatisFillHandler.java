package com.cyberpeace.config.db;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

public class MybatisFillHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("createTime", new Date(), metaObject);
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("updateTime", new Date(), metaObject);
	}
}