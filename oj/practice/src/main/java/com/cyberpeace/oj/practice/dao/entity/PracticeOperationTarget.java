package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

/**
 * 实践实操目标
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Getter
@Setter
@TableName("oj_practice_operation_target")
public class PracticeOperationTarget extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 实操id
	 *
	 * @see String
	 */
	private String operationId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	private String nodeId;

	/**
	 * 节点名称
	 *
	 * @see String
	 */
	private String nodeName;

	/**
	 * 附件id
	 *
	 * @see String
	 */
	private String attachmentId;

	/**
	 * node 命令
	 *
	 * @see String
	 */
	private String nodeCommand;
}
