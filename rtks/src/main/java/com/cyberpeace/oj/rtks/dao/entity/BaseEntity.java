package com.cyberpeace.oj.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class BaseEntity extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

}
