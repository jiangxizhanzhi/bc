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
 * @version 1.0.0
 * @date 2024/11/07
 * @see RecordRspBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RecordDetail extends RecordRspBase implements Serializable {

	/**
	 * 训练名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	/**
	 * 实习名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练人员")
	private String traineeName;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练时长（预计）")
	private Integer durationTime;

	/**
	 * 子类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "训练类别")
	private Integer subType;

	/**
	 * 子类型字符串
	 *
	 * @see String
	 */
	private String subTypeString;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "难度")
	private Integer difficulty;
	/**
	 * difficulty 字符串
	 *
	 * @see String
	 */
	private String difficultyString;


	/**
	 * 训练技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "训练技能")
	private List<String> trainSkill;

	/**
	 * 预技能
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练描述")
	private String description;

	/**
	 * 记录统计信息
	 *
	 * @see RecordStatistics
	 */
	@ApiModelProperty(value = "训练统计")
	private RecordStatistics recordStatistics;

	/**
	 * 雷达
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty(value = "技能评分")
	private List<Integer> radars;

	/**
	 * 雷达字符串
	 *
	 * @see String
	 */
	private String radarsString;

	/**
	 * 雷达名称字符串
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "雷达名称html字符串")
	private String radarNamesString;

	/**
	 * 阶段
	 *
	 * @see List<RecordStageBo>
	 */
	@ApiModelProperty(value = "任务详情")
	private List<RecordStageBo> stages = Lists.newArrayList();

	/**
	 * 指数
	 *
	 * @see Integer
	 */
	private Integer index;
}
