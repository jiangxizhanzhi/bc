package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_ioc_threat")
public class SpaceIocThreat extends SpaceBaseTable {
	@ApiModelProperty(value = "类型")
	private Integer type;
	@ApiModelProperty(value = "IOC列表")
	private String ioc;
}
