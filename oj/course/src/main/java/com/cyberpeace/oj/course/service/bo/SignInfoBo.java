package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class SignInfoBo implements Serializable {

	@ApiModelProperty("完成人数")
	private Integer finishCount = 0;

	@ApiModelProperty("签到人数据")
	private Integer isSign = 0;

	@ApiModelProperty("未签到人数")
	private Integer notSign = 0;

	@ApiModelProperty("组织信息")
	private String organizationInfo;

}
