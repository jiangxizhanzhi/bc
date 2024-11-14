package com.cyberpeace.cr.singletrain.service.bo.record;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.cr.crbase.service.context.score.RecordStatistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 记录详情
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RecordDetail extends RecordRspBase implements Serializable {

	/**
	 * 训练名称
	 */
	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	/**
	 * 训练人员
	 */
	@ApiModelProperty(value = "训练人员")
	private String traineeName;

	/**
	 * 训练时长（预计）
	 */
	@ApiModelProperty(value = "训练时长（预计）")
	private Integer durationTime;

	/**
	 * 训练类别
	 */
	@ApiModelProperty(value = "训练类别")
	private Integer subType;

	/**
	 * 子类型字符串
	 */
	private String subTypeString;

	/**
	 * 难度
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficulty;
	/**
	 * 难度字符串
	 */
	private String difficultyString;


	/**
	 * 训练技能
	 */
	@ApiModelProperty(value = "训练技能")
	private List<String> trainSkill;

	/**
	 * 前置技能
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 训练描述
	 */
	@ApiModelProperty(value = "训练描述")
	private String description;

	/**
	 * 训练统计
	 */
	@ApiModelProperty(value = "训练统计")
	private RecordStatistics recordStatistics;

	/**
	 * 技能评分
	 */
	@ApiModelProperty(value = "技能评分")
	private List<Integer> radars;

	/**
	 * 雷达字符串
	 */
	private String radarsString;

	/**
	 * 雷达名称html字符串
	 */
	@ApiModelProperty(value = "雷达名称html字符串")
	private String radarNamesString;

	/**
	 * 任务详情
	 */
	@ApiModelProperty(value = "任务详情")
	private List<RecordStageBo> stages = Lists.newArrayList();

	/**
	 * 编号
	 */
	private Integer index;
}
