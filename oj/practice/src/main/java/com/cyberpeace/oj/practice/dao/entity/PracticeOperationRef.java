package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * 派生于PracticeOperation, 在试卷中被引用
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("oj_practice_operation_ref")
public class PracticeOperationRef extends BaseTable {

	/**
	 * 快照 对应习题的快照
	 */
	private String snapshot;

	/**
	 * 资源编号
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 对应原习题编号
	 */
	private String originId;

	/**
	 * 试卷编号
	 */
	private String paperId;

}
