package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.space.model.SpaceBaseTable;
import lombok.Data;

@Data
@TableName("space_traffic_extraction_rule")
public class SpaceTrafficExtractionRule extends SpaceBaseTable {

	private String order;

	private String filename;

	private String route;

}
