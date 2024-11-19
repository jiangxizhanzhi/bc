package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 能力
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("rtks_ability")
public class Ability extends BaseTable {

	/**
	 * 能力id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	@ApiModelProperty("能力id")
	private String resourceId;

	/**
	 * 能力名称
	 *
	 * @see String
	 */
	@ApiModelProperty("能力名称")
	private String content;
}
