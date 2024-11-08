package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 实践实操目标
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("oj_practice_operation_target")
public class PracticeOperationTarget extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 实操id
	 */
	private String operationId;

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 节点名称
	 */
	private String nodeName;

	/**
	 * 附件id
	 */
	private String attachmentId;

	/**
	 * node 命令
	 */
	private String nodeCommand;
}
