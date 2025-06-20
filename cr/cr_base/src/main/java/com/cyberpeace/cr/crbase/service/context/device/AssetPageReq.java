package com.cyberpeace.cr.crbase.service.context.device;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 资产清单
 *
 * @author SN
 * @date 2023/05/29
 */
@Getter
@Setter
public class AssetPageReq extends PageReqBase implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "训练id", required = true)
	private String resource_id;

	/**
	 * 系统类型名称
	 */
	@ApiModelProperty(value = "操作系统名称", required = false)
	private String systemTypeName;
}
