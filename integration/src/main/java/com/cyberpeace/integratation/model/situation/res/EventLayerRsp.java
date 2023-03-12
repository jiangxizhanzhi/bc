package com.cyberpeace.integratation.model.situation.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.layer.LayerEventContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventLayerRsp implements Serializable {

	@ApiModelProperty("事件列表")
	private List<LayerEventContext> events;
}
