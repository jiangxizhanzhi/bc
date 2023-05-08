package com.cyberpeace.situation.dao.entity;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.constant.dashboard.PublicStatus;
import com.cyberpeace.situation.constant.dashboard.ReplayMode;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("situation_dashboard_instance")
public class SituationPublishInstance extends BaseTable {

	@TableId(value = "resource_id")
	private String resourceId;

	@ApiModelProperty("可视化大屏名称")
	private String name;

	@ApiModelProperty("大屏描述")
	private String description;

	@ApiModelProperty("特定事件")
	private String events;

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

	@ApiModelProperty("三方靶标URL")
	private String thirdUrl;

	@ApiModelProperty("重演模式")
	private Integer replayMode = ReplayMode.NONE.getCode();

	@ApiModelProperty("重演状态 0-无状态 1-开始 2-终止")
	private Integer replayStatus = 0;

	@ApiModelProperty("是否为想定大屏")
	private Boolean xd = false;

	@ApiModelProperty("类型")
	private String category;

	@ApiModelProperty("发布状态（草稿/发布）")
	private String status = "1";

	@ApiModelProperty("下发状态")
	private Integer publicStatus = PublicStatus.PREPARATION.getCode();

	@ApiModelProperty("是否可编辑")
	private Boolean edit = false;

	private String createUserId;

	public void setEventList(List<List<String>> events) {
		this.events = JsonUtil.toJsonString(events);
	}

	public List<List<String>> getEventList() {
		List<List<String>> eventList = Lists.newArrayList();

		if (this.events == null) {
			return eventList;
		}

		JSONArray list = JSONArray.parseArray(this.events);
		for (int i = 0; i < list.size(); i++) {
			eventList.add(JsonUtil.toJsonObjectArray(list.getString(i), String.class));
		}
		return eventList;
	}
}
