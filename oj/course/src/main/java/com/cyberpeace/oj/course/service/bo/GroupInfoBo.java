package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.model.user.UserInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class GroupInfoBo implements Serializable {

	@ApiModelProperty("组号")
	private Integer groupNo;

	@ApiModelProperty("组员编号")
	private String groupUserIds;

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("组员信息")
	private List<UserInfo> userInfos;

}
