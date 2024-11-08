package com.cyberpeace.oj.course.service.bo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 集团信息 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class GroupInfoBo extends ArchiveGroupBo {

	/**
	 * 场景数据
	 */
	@ApiModelProperty("场景信息")
	private JSONObject sceneData;

	/**
	 * 用户信息
	 */
	@ApiModelProperty("组员信息")
	private List<SimpleUserData> userInfos;

}
