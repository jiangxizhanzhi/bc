package com.cyberpeace.cr.teamtrain.model.room;

import java.util.List;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class TeamRoomReq extends PageReqBase {

	@ApiModelProperty(value = "类别ID")
	@Getter
	@Setter
	private String category;

	@ApiModelProperty(value = "检索参数")
	@Getter
	@Setter
	private String search;

	@ApiModelProperty(value = "名称")
	@Getter
	@Setter
	private String name;

	@ApiModelProperty(value = "难度")
	@Getter
	@Setter
	private Integer difficult;

	@Getter
	@Setter
	private List<String> ids;

}
