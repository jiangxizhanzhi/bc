package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.context.mission.MissionOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估任务响应
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class EvaluationMissionRsp implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "评估id，submit_id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "任务名称")
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "任务内容")
	private String description;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "任务类型")
	private Integer type;

	/**
	 * 选项
	 */
	@ApiModelProperty(value = "选择题选项")
	private List<MissionOption> option;

	/**
	 * 用户答案
	 */
	@ApiModelProperty(value = "用户答案")
	private  String userAnswer;

	/**
	 * 答案
	 */
	@ApiModelProperty(value = "标准答案")
	private String answer;

	/**
	 * 技能点
	 */
	@ApiModelProperty(value = "技能点")
	private List<Integer> skillPoints;

	/**
	 * 题解
	 */
	@ApiModelProperty(value = "题解")
	private String writeUp;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "满分值")
	private Integer score;
}
