package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Data
public class EvalTemplateListQuery extends PageReqBase implements Serializable {

	@ApiModelProperty(name = "category_code", value = "测评类型")
	@Getter
	@Setter
	private Integer categoryCode;

	private Integer type;

	@Getter
	@Setter
	private Integer isTem;

	public void setCategory_code(Integer categoryCode) {
		this.categoryCode = categoryCode;
	}

}
