package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.ElementInfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsSingleNodeEffect implements Serializable {

	@ApiModelProperty("场景id")
	private String sceneId;

	@ApiModelProperty("消息编码")
	private String code;

	@ApiModelProperty("动效名称")
	private String name;

	@ApiModelProperty("节点信息（更新部分）")
	private ElementInfo elementInfo;

}
