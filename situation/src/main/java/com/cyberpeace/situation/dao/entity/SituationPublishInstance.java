package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("situation_publish_instance")
public class SituationPublishInstance extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("可视化大屏名称")
	private String name;

	@ApiModelProperty("大屏描述")
	private String description;

	@ApiModelProperty("想定方案id")
	private String planId;

	@ApiModelProperty("任务id")
	private String taskId;

	@ApiModelProperty("科目id")
	private String subjectId;

	@ApiModelProperty("模板id")
	private String templateId;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("类型")
	private String category;

	@ApiModelProperty("状态")
	private String status;

	private String createUserId;
}
