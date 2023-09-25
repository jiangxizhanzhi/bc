package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.model.ElementType;
import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.model.WebAttackType;
import com.cyberpeace.situation.model.WebDefenseType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OptionsRes implements Serializable {

	@ApiModelProperty("动效类型")
	private List<WebAttackType> attackTypeList;

	@ApiModelProperty("防御事件类型")
	private List<WebDefenseType> defenseList;

	@ApiModelProperty("线型")
	private List<LocalType> lineTypeList;

	@ApiModelProperty("颜色")
	private List<LocalType> colorList;

	@ApiModelProperty("节点id和名称")
	private List<ElementType> elementList;

	@ApiModelProperty("区域id和名称")
	private List<ElementType> areaList;

	@ApiModelProperty("漏洞列表")
	private List<String> leaks;

	@ApiModelProperty("工具列表")
	private List<String> tools;

}
