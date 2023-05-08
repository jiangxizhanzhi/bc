package com.cyberpeace.cr.crbase.service.context.template;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 业务模板
 * 训练模板简要信息
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class BusinessTemplateSimple implements Serializable {

	@ApiModelProperty("id")
	private String resourceId;

	@ApiModelProperty("场景名称")
	private String name;

	@ApiModelProperty("场景类型")
	private Integer type;

	@ApiModelProperty("难度")
	private Integer difficulty;
}
