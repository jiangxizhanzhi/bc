package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * EVAL 模板列表查询
 *
 * @author SN
 * @date 2024/11/08
 */
@ApiModel
public class EvalTemplateListQuery extends PageReqBase implements Serializable {

	/**
	 * 类别代号
	 */
	@ApiModelProperty(name = "category_code", value = "测评类型")
	@Getter
	@Setter
	private Integer categoryCode;

	public void setCategory_code(Integer categoryCode) {
		this.categoryCode = categoryCode;
	}

}
