package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateItems;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimateItemsContext extends EstimateItems implements Serializable {

	/** 关联的指标项id*/
	@ApiModelProperty("关联的指标项id")
	private List<String> aggregation = Lists.newArrayList();

}
