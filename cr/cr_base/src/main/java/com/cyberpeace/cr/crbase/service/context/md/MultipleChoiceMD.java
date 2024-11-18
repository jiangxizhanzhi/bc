package com.cyberpeace.cr.crbase.service.context.md;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.QuestionTypeConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.common.mdparser.parser.ListParser;
import com.cyberpeace.cr.crbase.service.context.paraser.MultipleChoiceParser;

import lombok.Data;

/**
 * 多选题MarkDown
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MultipleChoiceMD implements Serializable {
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

	/**
	 * 选项
	 *
	 * @see List<String>
	 */
	@MdHeadLine(level = 3, name = "题目选项", parser = ListParser.class)
	private List<String> options;

	/**
	 * 答案
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "答案", parser = MultipleChoiceParser.class)
	private String answer;

}
