package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class TeamRoomListReq extends PageReqBase {

	@ApiModelProperty(value = "类别ID")
	private String category;

	@ApiModelProperty(value = "检索参数")
	private String search;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "难度")
	private Integer difficult;

	@ApiModelProperty(value = "id列表")
	private List<String> ids;

	@ApiModelProperty(value = "训练分类")
	private Integer trainCategory;

	@ApiModelProperty(value = "场景分类")
	private Integer sceneType;
}
