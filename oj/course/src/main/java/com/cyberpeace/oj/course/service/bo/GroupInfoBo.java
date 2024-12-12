package com.cyberpeace.oj.course.service.bo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 集团信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ArchiveGroupBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class GroupInfoBo extends ArchiveGroupBo {

	/**
	 * 场景数据
	 *
	 * @see JSONObject
	 */
	@ApiModelProperty("场景信息")
	private JSONObject sceneData;

	/**
	 * 用户信息
	 *
	 * @see List<SimpleUserData>
	 */
	@ApiModelProperty("组员信息")
	private List<SimpleUserData> userInfos;

}
