package com.cyberpeace.assess.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.cyberpeace.assess.constant.AssessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AssessMaterialAddReq implements Serializable {

	@ApiModelProperty(value = "评估材料名称")
	@NotNull(message = AssessErrorCode.NAME_MISSING)
	private String name;

	@ApiModelProperty(value = "评估案例id")
	@NotNull(message = AssessErrorCode.NAME_MISSING)
	private String caseId;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "类型")
	@NotNull(message = AssessErrorCode.MATERIAL_TYPE_MISSING)
	private String cover;

	@ApiModelProperty(value = "材料附件")
	@NotNull(message = AssessErrorCode.MATERIAL_ATTACHMENT_MISSING)
	private MultipartFile attachment;
}
