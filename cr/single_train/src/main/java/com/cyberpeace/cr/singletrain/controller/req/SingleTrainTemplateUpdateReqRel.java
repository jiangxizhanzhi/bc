package com.cyberpeace.cr.singletrain.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单训练模板更新请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("单兵训练模板更新")
public class SingleTrainTemplateUpdateReqRel extends SingleTrainTemplateReqRel implements Serializable {

	/**
	 * 模板业务id
	 */
	@ApiModelProperty("模板业务id")
    private String resourceId;

}
