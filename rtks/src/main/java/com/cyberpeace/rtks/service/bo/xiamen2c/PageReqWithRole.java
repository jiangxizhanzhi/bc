package com.cyberpeace.rtks.service.bo.xiamen2c;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 仪表盘-单兵训练人员评估-分页请求体
 * @author SN
 */
@ApiModel
public class PageReqWithRole extends PageReqBase {

	@ApiModelProperty(value = "岗位id")
	@Getter
	@Setter
	private String role_id;
}
