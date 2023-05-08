package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.ElementType;
import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.model.WebAttackType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 大屏选项数据返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class OptionsRes implements Serializable {

	/**
	 * 动效类型
	 */
	@ApiModelProperty("动效类型")
	private List<WebAttackType> attackTypeList;

	/**
	 * 线型
	 */
	@ApiModelProperty("线型")
	private List<LocalType> lineTypeList;

	/**
	 * 颜色
	 */
	@ApiModelProperty("颜色")
	private List<LocalType> colorList;

	/**
	 * 节点id和名称
	 */
	@ApiModelProperty("节点id和名称")
	private List<ElementType> elementList;

	/**
	 * 区域id和名称
	 */
	@ApiModelProperty("区域id和名称")
	private List<ElementType> areaList;

}
