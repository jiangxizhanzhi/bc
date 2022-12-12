package com.cyberpeace.integratation.model.situation.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.NodeActionContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LgNodeActionPageRsp extends LgPageResBase implements Serializable {

	@ApiModelProperty(value = "节点行为列表")
	private List<NodeActionContext> list;
}
