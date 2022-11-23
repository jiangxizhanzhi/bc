package com.cyberpeace.assess.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.assess.constant.AssessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssessMateriaUpdateReq extends AssessMaterialAddReq {

	@ApiModelProperty(value = "资源id")
	@NotNull(message = AssessErrorCode.RESOURCE_ID_MISSING)
	private String resourceId;
}
