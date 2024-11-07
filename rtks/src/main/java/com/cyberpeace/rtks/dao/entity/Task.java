package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_task")
public class Task extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 内容
	 */
	@ApiModelProperty("名称")
	private String content;
}
