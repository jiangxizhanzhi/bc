package com.cyberpeace.bs.controller.req;

import java.io.Serializable;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class IdReq implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;

	public String getResourceId(){
		if(StringUtils.isBlank(resourceId)){
			throw new BusinessException(BsErrorCode.RESOURCE_ID_MISSING);
		}
		return resourceId;
	}
}
