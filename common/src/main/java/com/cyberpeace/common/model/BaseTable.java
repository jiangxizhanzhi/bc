package com.cyberpeace.common.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基表
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class BaseTable implements Serializable {

	/**
	 * id
	 *
	 * @see Integer
	 */
	@ApiModelProperty("自增ID")
	private Integer id;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
}
