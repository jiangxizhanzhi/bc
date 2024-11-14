package com.cyberpeace.cr.singletrain.service.bo;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单模板Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("单兵训练模板")
public class SingleTemplateBo implements Serializable {

	/**
	 * 业务模板id
	 */
	@ApiModelProperty("业务模板id")
    private String resourceId;

	/**
	 * 基础信息
	 */
	@ApiModelProperty("基础信息")
    private EventDataBo eventData;

	/**
	 * 任务方案
	 */
	@ApiModelProperty("任务方案")
    private JSONObject missionsScheme;

	/**
	 * 拓扑id
	 */
	@ApiModelProperty("拓扑id")
    private String copiedTopoId;

}
