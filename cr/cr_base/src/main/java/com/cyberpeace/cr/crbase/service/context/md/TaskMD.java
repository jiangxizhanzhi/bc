package com.cyberpeace.cr.crbase.service.context.md;

import java.io.Serializable;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.QuestionTypeConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;

import lombok.Data;

/**
 * 任务MarkDown
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TaskMD implements Serializable {

	/**
	 * 标题
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "任务名称")
	private String title;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "简述")
	private String description;

	/**
	 * 任务类型
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "任务分类")
	private String taskType;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "分数", parser = IntegerParser.class)
	private Integer score;

	/**
	 * 技能点
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "技能点")
	private String skillPoints;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "题目类型", convert = QuestionTypeConvert.class)
	private Integer type;

}
