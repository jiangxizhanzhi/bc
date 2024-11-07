package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务详情
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionDetail implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 选择
	 */
	@ApiModelProperty("选项")
	private List<MissionOption> option;

	/**
	 * 技能点
	 */
	@ApiModelProperty(value = "技能点")
	private List<Integer> skillPoints;

	/**
	 * 类型
	 */
	@ApiModelProperty("任务类型")
	private Integer type;

	/**
	 * 类别id
	 */
	@ApiModelProperty("任务类别id")
	private String categoryId;

	/**
	 * 描述
	 */
	@ApiModelProperty("任务描述")
	private String description;

	/**
	 * 写入向上
	 */
	@ApiModelProperty("任务题解")
	private String writeUp;

	/**
	 * 显示 Writeup
	 */
	@ApiModelProperty("是否展示任务题解")
	private Boolean showWriteup;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案")
	private String answer;

	/**
	 * 得分
	 */
	@ApiModelProperty("分值")
	private Integer score;
}
