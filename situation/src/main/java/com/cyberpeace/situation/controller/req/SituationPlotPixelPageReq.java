package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 标绘图元分页查询请求
 *
 * @Author xuxz
 * @Date 15/12/2022 上午11:18
 */
@Data
public class SituationPlotPixelPageReq extends PageReqBase implements Serializable {

	/**
	 * 图标名称
	 */
	@ApiModelProperty("图标名称")
	private String name;

}
