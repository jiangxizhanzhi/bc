package com.cyberpeace.cr.evaluation.service.context;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description: 测评角色配置
 * @author zhangjiaxin
 * @date 2022年9月22日下午3:29:44 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EvalRoleConfig extends CrRoleConfig {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色类型", required = true)
	@NotNull
	private String type;
	
	@ApiModelProperty(value = "角色用户", required = true)
	@NotNull
	private List<String> users;
	
	@ApiModelProperty(value = "步骤配置", required = true)
	@NotNull
	private List<EvalTaskStepConfig> steps;

}
