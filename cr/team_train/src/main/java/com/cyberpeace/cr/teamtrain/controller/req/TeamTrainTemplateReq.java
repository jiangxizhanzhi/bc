package com.cyberpeace.cr.teamtrain.controller.req;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TeamTrainTemplateReq extends BasicTemplate {

	@ApiModelProperty(value = "角色列表", required = true)
	@NotNull
	private List<CrRoleConfig> roleList;

	private Integer peopleNumber;

	@ApiModelProperty(value = "时间轴配置", required = true)
	@NotNull
	private Object timeLineConfig = "{}";

}
