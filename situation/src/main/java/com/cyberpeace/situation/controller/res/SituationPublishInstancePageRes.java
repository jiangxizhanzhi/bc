package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.situation.constant.dashboard.PublicStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 13/12/2022 下午8:55
 */
@Data
public class SituationPublishInstancePageRes implements Serializable {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "类型")
	private String category;

	@ApiModelProperty(value = "大屏状态")
	private String status;

	@ApiModelProperty(value = "大屏发布状态")
	private Integer publicStatus;

	@ApiModelProperty(value = "任务名称")
	private String taskName;

	@ApiModelProperty(value = "科目名称")
	private String subjectName;

	@ApiModelProperty(value = "任务id")
	private String taskId;

	@ApiModelProperty(value = "科目id")
	private String subjectId;

	@ApiModelProperty(value = "可视化方案名称")
	private String schemeName;

	@ApiModelProperty(value = "模板id")
	private String templateId;

	@ApiModelProperty(value = "大屏属性ID")
	private String attributeId;

	@ApiModelProperty(value = "是否可编辑")
	private Boolean edit;

	@ApiModelProperty(value = "是否为想定大屏")
	private Boolean xd;

	@ApiModelProperty(value = "模板可编辑")
	private Boolean templateEdit;

	@ApiModelProperty("重演模式")
	private Integer replayMode;

	@ApiModelProperty("重演状态 0-无状态 1-开始 2-终止")
	private Integer replayStatus;

	@ApiModelProperty(value = "任务状态")
	private String taskStatus;

	@ApiModelProperty(value = "科目状态")
	private String subjectStatus;

	@ApiModelProperty(value = "开始时间")
	private String startTime;

	@ApiModelProperty(value = "结束时间")
	private String endTime;

	@ApiModelProperty(value = "更新时间")
	private String updateTime;

	@ApiModelProperty(value = "重置按钮名称")
	private String resetButton;

	@ApiModelProperty(value = "完成按钮名称")
	private String finishButton;

	@ApiModelProperty(value = "是否预览")
	private Boolean preview;

	public void setButton() {
		PublicStatus publicStatus = PublicStatus.getPublicStatusByCode(this.publicStatus);
		this.resetButton = xd ? publicStatus.getResetButtonXd() : publicStatus.getResetButton();
		this.finishButton = xd ? publicStatus.getFinishButtonXd() : publicStatus.getFinishButton();
		this.status = publicStatus.getName();
		this.preview = publicStatus.getPreview();
	}

}
