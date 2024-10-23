package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_finger_print")
public class SpaceFingerPrint extends SpaceBaseTable {

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "指纹列表")
	private String content;

}
