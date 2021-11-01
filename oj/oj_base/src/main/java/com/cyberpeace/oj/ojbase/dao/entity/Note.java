package com.cyberpeace.oj.ojbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

import java.lang.reflect.Type;

@Data
@TableName("oj_note")
public class Note extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Integer readAuthType;

	private String readAuthIds;

	private Integer operationAuthType;

	private String operationAuthIds;

	private Integer noteType;

	private Integer businessType;

	private String content;

	private String userId;

	private String businessId;

}
