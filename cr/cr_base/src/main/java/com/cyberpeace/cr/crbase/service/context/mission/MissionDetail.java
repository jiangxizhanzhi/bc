package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务详情
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionDetail implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 选择
	 *
	 * @see List<MissionOption>
	 */
	@ApiModelProperty("选项")
	private List<MissionOption> option;

	/**
	 * 技能点
	 *
	 * @see List<Integer>
	 */
	@ApiModelProperty(value = "技能点")
	private List<Integer> skillPoints;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("任务类别id")
	private String categoryId;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 写入向上
	 *
	 * @see String
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 显示 Writeup
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否展示任务题解")
	private Boolean showWriteup;

	/**
	 * 答案
	 *
	 * @see String
	 */
	@ApiModelProperty("答案")
	private String answer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("分值")
	private Integer score;
}
