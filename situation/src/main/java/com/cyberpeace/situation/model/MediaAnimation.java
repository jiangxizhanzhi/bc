package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MediaAnimation implements Serializable {

	@ApiModelProperty("方向：1=左上、2=左下、3=右上、4=右下")
	private Integer direction;

	@ApiModelProperty("资源路径")
	private String url;

	@ApiModelProperty("源节点id")
	private String sourceId;

}
