package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("oj_practice_operation_target")
public class PracticeOperationTarget extends BaseTable {

	/** 资源id*/
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String operationId;

	private String nodeId;

	private String nodeName;

	private String attachmentId;

	private String nodeCommand;
}
