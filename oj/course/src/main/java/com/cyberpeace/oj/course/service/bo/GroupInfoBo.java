package com.cyberpeace.oj.course.service.bo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组信息Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class GroupInfoBo extends ArchiveGroupBo {

	/**
	 * 场景信息
	 */
	@ApiModelProperty("场景信息")
	private JSONObject sceneData;

	/**
	 * 组员信息
	 */
	@ApiModelProperty("组员信息")
	private List<SimpleUserData> userInfos;

}
