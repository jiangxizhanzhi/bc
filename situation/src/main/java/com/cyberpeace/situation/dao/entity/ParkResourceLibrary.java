package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situational_park_resource_library")
public class ParkResourceLibrary extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("园区资源名称")
	private String name;

	@ApiModelProperty("资源类型")
	private String parkResourceLibraryCategory;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("资源文件 u3d格式")
	private String resourceFile;

	private String createUserId;

}
