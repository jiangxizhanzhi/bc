package com.cyberpeace.rtks.service.bo.career;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.template.BTPageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 详情分页请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class DetailPageReq extends BTPageReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	@NotNull
	private String resourceId;

}
