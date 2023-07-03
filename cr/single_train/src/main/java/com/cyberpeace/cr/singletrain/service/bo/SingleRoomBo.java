package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单兵训练详情")
public class SingleRoomBo extends SingleTemplateBo implements Serializable {

	@ApiModelProperty("训练模板id")
	private String resourceId;

	@ApiModelProperty("用户使用训练")
	private SingleRoomInfoBo roomInfo;

	@ApiModelProperty("本地场景id")
	private String localSceneId;

	@ApiModelProperty("训练是否可提交")
	private Boolean showSubmitBtn;

	@ApiModelProperty("拓扑id")
	private String copiedTopoId;

}
