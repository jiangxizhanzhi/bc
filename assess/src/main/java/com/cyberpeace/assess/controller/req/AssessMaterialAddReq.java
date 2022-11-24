package com.cyberpeace.assess.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.assess.constant.AssessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AssessMaterialAddReq implements Serializable {

	@ApiModelProperty(value = "评估材料名称")
	@NotBlank(message = AssessErrorCode.NAME_MISSING)
	private String name;

	@ApiModelProperty(value = "评估案例id")
	@NotBlank(message = AssessErrorCode.NAME_MISSING)
	private String caseId;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "类型")
	@NotNull(message = AssessErrorCode.MATERIAL_TYPE_MISSING)
	private Integer materialType;

	@ApiModelProperty(value = "材料附件")
	@NotBlank(message = AssessErrorCode.MATERIAL_ATTACHMENT_MISSING)
	private MultipartFile attachment;
}
