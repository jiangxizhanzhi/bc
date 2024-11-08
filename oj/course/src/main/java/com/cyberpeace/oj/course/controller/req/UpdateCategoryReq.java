package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 更新类别请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class UpdateCategoryReq extends SaveCourseCategoryReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

}
