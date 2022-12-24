package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_publish_instance")
public class SituationPublishInstance extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("可视化大屏名称")
	private String name;

	@ApiModelProperty("大屏描述")
	private String description;

	@ApiModelProperty("大屏属性ID")
	private String attributeId;

	@ApiModelProperty("可视化方案ID")
	private String schemeId;

	@ApiModelProperty("分靶场ID")
	private String subRangeId;

	@ApiModelProperty("分靶场名称")
	private String subRangeName;

	@ApiModelProperty("任务ID")
	private String taskId;

	@ApiModelProperty("任务名称")
	private String taskName;

	@ApiModelProperty("科目ID")
	private String subjectId;

	@ApiModelProperty("科目名称")
	private String subjectName;

	@ApiModelProperty("模板ID")
	private String templateId;

	@ApiModelProperty("缩略图")
	private String thumbnail;

	@ApiModelProperty("类型")
	private String category;

	@ApiModelProperty("发布状态（草稿/发布）")
	private String status;

	@ApiModelProperty("下发状态（下发/未下发）")
	private Integer publicStatus;

	@ApiModelProperty("是否可编辑")
	private Boolean edit;

	private String createUserId;
}
