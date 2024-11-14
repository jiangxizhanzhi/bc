package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Web工具列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class WebToolListReq extends PageReqBase {

	/**
	 * 分类
	 */
	@ApiModelProperty("分类")
    private String category;

	/**
	 * 平台
	 */
	@ApiModelProperty("平台")
    private String platforms;

	/**
	 * 授权
	 */
	@ApiModelProperty("授权")
    private String cracked;
}
