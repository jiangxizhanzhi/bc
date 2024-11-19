package com.cyberpeace.rtks.service.bo.career;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.crbase.service.context.template.BTPageReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 详情分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BTPageReq
 * @since 1.0.0
 */
@Data
public class DetailPageReq extends BTPageReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源id")
	@NotNull
	private String resourceId;

}
