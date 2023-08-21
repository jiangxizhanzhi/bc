package com.cyberpeace.cr.crbase.controller.req.scene;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class DailyTemplateReq extends PageReqBase {

	@ApiModelProperty(value = "类别ID")
	private String type;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "难度")
	private String difficult;

	@ApiModelProperty("场景类型")
	private Integer sceneType;

	@ApiModelProperty(value = "红/蓝训练区分, 1=红;2=蓝;3=日常练习")
	private Integer trainCategory;

}
