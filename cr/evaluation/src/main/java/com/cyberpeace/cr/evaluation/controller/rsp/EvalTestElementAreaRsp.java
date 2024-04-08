package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.situation.model.ElementType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalTestElementAreaRsp implements Serializable {

	@ApiModelProperty("节点列表")
	private List<ElementType> elementList;

	@ApiModelProperty("区域列表")
	private List<ElementType> areaList;
}
