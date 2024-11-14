package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("rtks_task")
public class Task extends BaseTable {

	/**
	 * 任务id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String content;
}
