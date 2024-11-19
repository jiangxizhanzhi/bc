package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 媒体动画
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MediaAnimation implements Serializable {

	/**
	 * 方向：1=左上、2=左下、3=右上、4=右下
	 *
	 * @see Integer
	 */
	@ApiModelProperty("方向：1=左上、2=左下、3=右上、4=右下")
	private Integer direction;

	/**
	 * 资源路径
	 *
	 * @see String
	 */
	@ApiModelProperty("资源路径")
	private String url;

	/**
	 * 源节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 媒体名称
	 *
	 * @see String
	 */
	@ApiModelProperty("媒体名称")
	private String name;

}
