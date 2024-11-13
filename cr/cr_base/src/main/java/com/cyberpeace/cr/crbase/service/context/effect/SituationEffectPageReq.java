package com.cyberpeace.cr.crbase.service.context.effect;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势动效分页请求体
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class SituationEffectPageReq extends PageReqBase {

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "分类id")
	private String categoryId;
}
