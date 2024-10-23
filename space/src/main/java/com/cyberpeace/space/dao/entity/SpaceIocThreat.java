package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.space.model.SpaceBaseTable;
import lombok.Data;

@Data
@TableName("space_ioc_threat")
public class SpaceIocThreat extends SpaceBaseTable {

	private Integer type;

	private String ioc;
}
