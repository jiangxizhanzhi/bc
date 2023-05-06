package com.cyberpeace.situation.controller.res;

import java.io.Serializable;

import com.cyberpeace.situation.constant.dashboard.PublicStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势发布实例分页返回
 *
 * @Author xuxz
 * @Date 13/12/2022 下午8:55
 */
@Data
public class SituationPublishInstancePageRes implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	private String category;

	/**
	 * 大屏状态
	 */
	@ApiModelProperty(value = "大屏状态")
	private String status;

	/**
	 * 大屏发布状态
	 */
	@ApiModelProperty(value = "大屏发布状态")
	private Integer publicStatus;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名称")
	private String taskName;

	/**
	 * 科目名称
	 */
	@ApiModelProperty(value = "科目名称")
	private String subjectName;

	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "任务id")
	private String taskId;

	/**
	 * 科目id
	 */
	@ApiModelProperty(value = "科目id")
	private String subjectId;

	/**
	 * 可视化方案名称
	 */
	@ApiModelProperty(value = "可视化方案名称")
	private String schemeName;

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "模板id")
	private String templateId;

	/**
	 * 大屏属性ID
	 */
	@ApiModelProperty(value = "大屏属性ID")
	private String attributeId;

	/**
	 * 是否可编辑
	 */
	@ApiModelProperty(value = "是否可编辑")
	private Boolean edit;

	/**
	 * 是否为想定大屏
	 */
	@ApiModelProperty(value = "是否为想定大屏")
	private Boolean xd;

	/**
	 * 模板可编辑
	 */
	@ApiModelProperty(value = "模板可编辑")
	private Boolean templateEdit;

	/**
	 * 重演模式
	 */
	@ApiModelProperty("重演模式")
	private Integer replayMode;

	/**
	 * 重演状态 0-无状态 1-开始 2-终止
	 */
	@ApiModelProperty("重演状态 0-无状态 1-开始 2-终止")
	private Integer replayStatus;

	/**
	 * 任务状态
	 */
	@ApiModelProperty(value = "任务状态")
	private String taskStatus;

	/**
	 * 科目状态
	 */
	@ApiModelProperty(value = "科目状态")
	private String subjectStatus;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private String endTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private String updateTime;

	/**
	 * 重置按钮名称
	 */
	@ApiModelProperty(value = "重置按钮名称")
	private String resetButton;

	/**
	 * 完成按钮名称
	 */
	@ApiModelProperty(value = "完成按钮名称")
	private String finishButton;

	/**
	 * 是否预览
	 */
	@ApiModelProperty(value = "是否预览")
	private Boolean preview;

	/**
	 * 设置按钮
	 */
	public void setButton() {
		PublicStatus publicStatus = PublicStatus.getPublicStatusByCode(this.publicStatus);
		this.resetButton = xd ? publicStatus.getResetButtonXd() : publicStatus.getResetButton();
		this.finishButton = xd ? publicStatus.getFinishButtonXd() : publicStatus.getFinishButton();
		this.status = publicStatus.getName();
		this.preview = publicStatus.getPreview();
	}

}
