package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situational_three_dimensional_components")
public class ThreeDimensionalComponents extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("组件名称")
	private String name;

	@ApiModelProperty("组件类型")
	private String threeDimensionalComponentsCategory;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("图表标准文件")
	private String chartStandardDocument;

	private String createUserId;

}
