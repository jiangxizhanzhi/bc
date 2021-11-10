package com.cyberpeace.config.custom;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.cyberpeace.oj.ojbase.dao.entity.ReadAndOperation;
import org.apache.ibatis.mapping.MappedStatement;

public class SelectByReadAndOperation {

	String RESOURCE_FIELD = "resource_id";

	String READ_SQL = "select %s from %s";

	String WHERE_ADMIN = "(create_user_id = %s or (operation_auth_ids like %s and operation_auth_type = "
			+ ReadAndOperation.AuthShare.CUSTOM.code + ") or operation_auth_type = "
			+ ReadAndOperation.AuthShare.ALL.code + ")";

	String WHERE_WEB = "(create_user_id = %s or (operation_auth_ids like %s and operation_auth_type = "
			+ ReadAndOperation.AuthShare.CUSTOM.code + ") or operation_auth_type = "
			+ ReadAndOperation.AuthShare.ALL.code + ")";


	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass,
			TableInfo tableInfo) {

		return null;
	}

}
