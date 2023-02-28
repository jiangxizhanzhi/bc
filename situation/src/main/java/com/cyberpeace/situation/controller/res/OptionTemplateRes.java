package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.model.WebAttackType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OptionTemplateRes implements Serializable {

	@ApiModelProperty("动效类型")
	private List<WebAttackType> attackTypeList;

	@ApiModelProperty("防御事件类型")
	private List<LocalType> defenseList;

	@ApiModelProperty("线型")
	private List<LocalType> lineTypeList;

	@ApiModelProperty("颜色")
	private List<LocalType> colorList;
}
