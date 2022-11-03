package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_park_resource_library_category")
public class ParkResourceLibraryCategory extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("类型名称")
	private String name;

	private String createUserId;

}
