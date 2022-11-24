package com.cyberpeace.assess.controller.req;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.assess.constant.AssessErrorCode;
import com.cyberpeace.assess.dao.mapper.AssessTagMapper;
import org.apache.commons.collections.CollectionUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("材料编排信息")
public class AssessCaseConfigUpdateRes implements Serializable {

	@ApiModelProperty("案例id")
	@NotBlank(message = AssessErrorCode.CASE_ID_ERROR)
	private String caseId;

	@ApiModelProperty("材料id")
	@NotEmpty(message = AssessErrorCode.MATERIAL_IDS_ERROR)
	private List<String> materialId;
}
