package com.cyberpeace.bs.service.bo.model;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增用户行为模拟
 */
@Data
public class UserModelAddBo extends UserModelBase implements Serializable {

	@ApiModelProperty(value = "简介")
	private String description;

	@ApiModelProperty(value = "访问行为")
	private List<BehaviorSimple> behaviors;

	@ApiModelProperty(value = "是否限制带宽")
	private Boolean bandwidthLimited;

	@ApiModelProperty(value = "访问带宽")
	private Integer bandwidth;

	public void validate() {
		CrCommonValidator.validateNameLen(this.getUserName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		CrCommonValidator.validateDescLen(this.description, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}
}
