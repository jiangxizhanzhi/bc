package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_dynamic_scheme_template")
public class DynamicSchemeTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	//资源编号
	private String resourceId;

	//方案名称
	private String name;

	//方案数据
	private String schemeData;

}
