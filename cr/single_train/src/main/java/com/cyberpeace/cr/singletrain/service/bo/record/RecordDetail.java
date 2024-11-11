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
 * @date 2024/11/07
 */
@Data
public class RecordDetail extends RecordRspBase implements Serializable {

	/**
	 * 训练名称
	 */
	@ApiModelProperty(value = "训练名称")
	private String trainingName;

	/**
	 * 实习名称
	 */
	@ApiModelProperty(value = "训练人员")
	private String traineeName;

	/**
	 * 持续时间
	 */
	@ApiModelProperty(value = "训练时长（预计）")
	private Integer durationTime;

	/**
	 * 子类型
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
	 * difficulty 字符串
	 */
	private String difficultyString;


	/**
	 * 训练技能
	 */
	@ApiModelProperty(value = "训练技能")
	private List<String> trainSkill;

	/**
	 * 预技能
	 */
	@ApiModelProperty(value = "前置技能")
	private List<String> preSkill;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "训练描述")
	private String description;

	/**
	 * 记录统计信息
	 */
	@ApiModelProperty(value = "训练统计")
	private RecordStatistics recordStatistics;

	/**
	 * 雷达
	 */
	@ApiModelProperty(value = "技能评分")
	private List<Integer> radars;

	/**
	 * 雷达字符串
	 */
	private String radarsString;

	/**
	 * 雷达名称字符串
	 */
	@ApiModelProperty(value = "雷达名称html字符串")
	private String radarNamesString;

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "任务详情")
	private List<RecordStageBo> stages = Lists.newArrayList();

	/**
	 * 指数
	 */
	private Integer index;
}
