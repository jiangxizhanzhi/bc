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
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class BaseTable implements Serializable {

	@ApiModelProperty("自增ID")
	private Integer id;

	@ApiModelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@ApiModelProperty("更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
}
