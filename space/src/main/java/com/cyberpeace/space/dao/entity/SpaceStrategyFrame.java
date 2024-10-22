package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
@TableName("space_strategy_frame")
public class SpaceStrategyFrame extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String resourceId;

	private String name;

	private String parent;

	private Boolean builtin;

}
