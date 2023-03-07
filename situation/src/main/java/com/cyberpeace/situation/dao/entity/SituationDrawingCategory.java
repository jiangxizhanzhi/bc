package com.cyberpeace.situation.dao.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 4/1/2023 下午1:47
 */
@Data
@TableName("situation_components_drawing_category")
public class SituationDrawingCategory extends BaseTable implements Serializable {

	@ApiModelProperty("资源id")
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	@ApiModelProperty("图形名称")
	private String name;
}
