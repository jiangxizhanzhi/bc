package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("rtks_ability")
public class Ability extends BaseTable {

	/**
	 * 能力id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("能力id")
	private String resourceId;

	/**
	 * 能力名称
	 */
	@ApiModelProperty("能力名称")
	private String content;
}
