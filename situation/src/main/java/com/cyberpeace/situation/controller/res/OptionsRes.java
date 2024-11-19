package com.cyberpeace.situation.controller.res;

import java.util.List;

import com.cyberpeace.situation.model.ElementType;
import com.cyberpeace.situation.model.LocalType;
import com.cyberpeace.situation.model.WebAttackType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 选项结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class OptionsRes {

	/**
	 * 动效类型
	 *
	 * @see List<WebAttackType>
	 */
	@ApiModelProperty("动效类型")
	private List<WebAttackType> attackTypeList;

	/**
	 * 线型
	 *
	 * @see List<LocalType>
	 */
	@ApiModelProperty("线型")
	private List<LocalType> lineTypeList;

	/**
	 * 颜色
	 *
	 * @see List<LocalType>
	 */
	@ApiModelProperty("颜色")
	private List<LocalType> colorList;

	/**
	 * 节点id和名称
	 *
	 * @see List<ElementType>
	 */
	@ApiModelProperty("节点id和名称")
	private List<ElementType> elementList;

	/**
	 * 区域id和名称
	 *
	 * @see List<ElementType>
	 */
	@ApiModelProperty("区域id和名称")
	private List<ElementType> areaList;

}
