package com.cyberpeace.situation.controller.res;

import java.util.List;

import com.cyberpeace.situation.model.ElementType;
import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.model.WebAttackType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OptionsRes {

	@ApiModelProperty("动效类型")
	private List<WebAttackType> attackTypeList;

	@ApiModelProperty("线型")
	private List<LocalType> lineTypeList;

	@ApiModelProperty("颜色")
	private List<LocalType> colorList;

	@ApiModelProperty("节点id和名称")
	private List<ElementType> elementList;

	@ApiModelProperty("区域id和名称")
	private List<ElementType> areaList;

}
