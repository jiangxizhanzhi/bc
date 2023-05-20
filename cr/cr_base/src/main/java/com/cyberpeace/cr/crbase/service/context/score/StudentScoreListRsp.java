package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentScoreListRsp implements Serializable {

	@ApiModelProperty(value = "学员id")
	private String userId;

	@ApiModelProperty(value = "学员姓名")
	private String name;

	@ApiModelProperty(value = "学员头像")
	private String userAvatar;

	@ApiModelProperty(value = "角色id")
	private String roleId;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "角色类型， 2:团队学员，4：红方，5：蓝方")
	private Integer roleGroup;

	@ApiModelProperty(value = "学员评分")
	private Integer score;

	@ApiModelProperty(value = "是否已被评分")
	private Boolean scored;

	@ApiModelProperty(value = "评分时间")
	private Date scoreTime;

}
