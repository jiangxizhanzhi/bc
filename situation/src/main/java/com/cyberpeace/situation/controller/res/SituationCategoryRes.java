package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.situation.dao.entity.SituationCategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势分类返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class SituationCategoryRes extends SituationCategory implements Serializable {

	/**
	 * 是否存在定制模板
	 */
	@ApiModelProperty("是否存在定制模板")
	private Boolean template = false;
}
