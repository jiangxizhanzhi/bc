package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.situation.dao.entity.SituationCategory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SituationCategoryRes extends SituationCategory implements Serializable {

	@ApiModelProperty("是否存在定制模板")
	private Boolean template = false;
}
