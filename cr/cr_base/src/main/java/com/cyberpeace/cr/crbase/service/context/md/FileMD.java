package com.cyberpeace.cr.crbase.service.context.md;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.QuestionTypeConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文件题Markdown
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FileMD implements Serializable {

	/**
	 * 标题
	 */
	@MdHeadLine(level = 3, name = "任务名称")
	private String title;

	/**
	 * 描述
	 */
	@MdHeadLine(level = 3, name = "简述")
	private String description;

	/**
	 * 任务类型
	 */
	@MdHeadLine(level = 3, name = "任务分类")
	private String taskType;

	/**
	 * 得分
	 */
	@MdHeadLine(level = 3, name = "分数", parser = IntegerParser.class)
	private Integer score;

	/**
	 * 技能点
	 */
	@MdHeadLine(level = 3, name = "技能点")
	private String skillPoints;

	/**
	 * 类型
	 */
	@MdHeadLine(level = 3, name = "题目类型", convert = QuestionTypeConvert.class)
	private Integer type;

	/**
	 * 答案
	 */
	@MdHeadLine(level = 3, name = "答案")
	private String answer;

}
