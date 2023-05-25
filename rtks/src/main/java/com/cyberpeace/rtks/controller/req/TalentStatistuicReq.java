package com.cyberpeace.rtks.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel
public class TalentStatistuicReq extends PageReqBase {

	@ApiModelProperty("组织id")
	private String orgId;

	@ApiModelProperty("岗位")
	private String roleId;

	@ApiModelProperty("姓名")
	private String userName;

}
