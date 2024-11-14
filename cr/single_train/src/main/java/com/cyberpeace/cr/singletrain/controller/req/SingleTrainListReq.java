package com.cyberpeace.cr.singletrain.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单训练列表请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("前台单兵训练分页查询")
public class SingleTrainListReq extends PageReqBase implements Serializable {
	/**
	 * 训练类别
	 */
	@ApiModelProperty("训练类别")
    private Integer category;

	/**
	 * 训练难度
	 */
	@ApiModelProperty("训练难度")
    private Integer difficult;

	/**
	 * with 实例
	 */
	private Integer withInstance;
}
