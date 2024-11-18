package com.cyberpeace.cr.crbase.service.context.device;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资产清单
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/29
 * @see PageReqBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AssetPageReq extends PageReqBase implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练id", required = true)
	private String resource_id;

	/**
	 * 系统类型名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "操作系统名称", required = false)
	private String systemTypeName;
}
