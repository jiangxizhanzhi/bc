package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MediaAnimation implements Serializable {

	/**
	 * 方向：1=左上、2=左下、3=右上、4=右下
	 */
	@ApiModelProperty("方向：1=左上、2=左下、3=右上、4=右下")
	private Integer direction;

	/**
	 * 资源路径
	 */
	@ApiModelProperty("资源路径")
	private String url;

	/**
	 * 源节点id
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 媒体名称
	 */
	@ApiModelProperty("媒体名称")
	private String name;

}
