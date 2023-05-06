package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.dao.entity.LayerShow;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图层展示请求体
 *
 * @Author xuxz
 * @Date 13/1/2023 上午11:50
 */
@Data
public class LayerShowReq extends LayerShow implements Serializable {

	/**
	 * 组合图层
	 */
	@ApiModelProperty("组合图层")
	private List<LayerShowReq> children;
}
