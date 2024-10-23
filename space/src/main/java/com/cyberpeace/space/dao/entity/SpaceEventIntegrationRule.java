
package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_event_integration_rule")
public class SpaceEventIntegrationRule extends SpaceBaseTable {
    @ApiModelProperty(value = "统计周期")
	private Integer cycle;
    @ApiModelProperty(value = "告警阈值")
	private Integer threshold;
}