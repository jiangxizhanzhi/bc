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
 * @version 1.0.0
 * @date 2024/11/14
 * @see ArchiveGroupBo
 * @since 1.0.0
 */
@Data
@ApiModel
public class GroupInfoBo extends ArchiveGroupBo {

	/**
	 * 场景信息
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景信息")
	private JSONObject sceneData;

	/**
	 * 组员信息
	 *
	 * @see List<SimpleUserData>
	 */
	@ApiModelProperty("组员信息")
	private List<SimpleUserData> userInfos;

}
