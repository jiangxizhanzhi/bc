package com.cyberpeace.config.db;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

public class MybatisFillHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		Object createTime = this.getFieldValByName("createTime", metaObject);
		Object updateTime = this.getFieldValByName("updateTime", metaObject);
		if (createTime == null) {
			this.setFieldValByName("createTime", new Date(), metaObject);
		}
		if (updateTime == null) {
			this.setFieldValByName("updateTime", new Date(), metaObject);
		}
	}
	@Override
	public void updateFill(MetaObject metaObject) {
		Object updateTime = this.getFieldValByName("updateTime", metaObject);
		if (updateTime == null) {
			this.setFieldValByName("updateTime", new Date(), metaObject);
		}
	}
}