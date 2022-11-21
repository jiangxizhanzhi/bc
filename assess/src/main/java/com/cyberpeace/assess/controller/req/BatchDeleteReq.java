package com.cyberpeace.assess.controller.req;

import com.cyberpeace.assess.constant.AssessErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
public class BatchDeleteReq implements Serializable {

	@ApiModelProperty("resource_id列表")
	@NotEmpty(message = AssessErrorCode.RESOURCE_IDS_MISSING)
	List<String> ids;
}
