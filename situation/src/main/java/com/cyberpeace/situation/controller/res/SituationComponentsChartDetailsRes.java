package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.dao.entity.TwoDimensionalComponents;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势图表组件详情返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationComponentsChartDetailsRes extends TwoDimensionalComponents {

	/**
	 * 组件类型名称
	 */
	@ApiModelProperty("组件类型名称")
	private String chartCategoryName;

}
