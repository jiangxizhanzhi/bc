package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@TableName("space_action_frame_use")
public class SpaceActionFrameUse {

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	private String type;

	/**
	 * 日期
	 */
	@ApiModelProperty("日期")
	@TableField("`date`")
	private String date;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@TableField("`count`")
	private Integer count;

}
