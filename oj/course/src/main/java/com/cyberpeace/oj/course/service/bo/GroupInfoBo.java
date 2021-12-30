package com.cyberpeace.oj.course.service.bo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GroupInfoBo extends ArchiveGroupBo {

	@ApiModelProperty("场景信息")
	private JSONObject sceneData;

	@ApiModelProperty("组员信息")
	private List<SimpleUserData> userInfos;

}
