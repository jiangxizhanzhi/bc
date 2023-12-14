package com.cyberpeace.oj.ojbase.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class UserNoteScoreBo implements Serializable {

	@ApiModelProperty("用户编号")
	private String userId;

	@ApiModelProperty("用户名称")
	private String userName;

	@ApiModelProperty("昵称")
	private String name;

	@ApiModelProperty("得分")
	private Double score;

}
