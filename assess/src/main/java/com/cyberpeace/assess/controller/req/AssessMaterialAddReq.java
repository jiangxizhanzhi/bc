package com.cyberpeace.assess.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.assess.constant.AssessErrorCode;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AssessMaterialAddReq implements Serializable {

	@ApiModelProperty(value = "评估材料名称")
	@NotBlank(groups = {Update.class, Create.class}, message = AssessErrorCode.NAME_MISSING)
	private String name;

	@ApiModelProperty(value = "评估案例id")
	@NotBlank(groups = {Update.class, Create.class}, message = AssessErrorCode.CASE_ID_EMPTY)
	private String caseId;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "类型")
	@NotNull(groups = {Update.class, Create.class}, message = AssessErrorCode.MATERIAL_TYPE_MISSING)
	private Integer materialType;

	@ApiModelProperty(value = "材料附件")
	@NotBlank(groups = {Update.class, Create.class}, message = AssessErrorCode.MATERIAL_ATTACHMENT_MISSING)
	private String attachment;

	@ApiModelProperty(value = "材料附件")
	@NotBlank(groups = {Update.class, Create.class}, message = AssessErrorCode.ATTACHMENT_NAME_MISSING)
	private String attachmentName;
}
