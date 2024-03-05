package com.cyberpeace.cr.crbase.service.context.md;

import java.io.Serializable;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.QuestionTypeConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;

import lombok.Data;

/**
 * 任务MarkDown
 */
@Data
public class TaskMD implements Serializable {

	@MdHeadLine(level = 3, name = "任务名称")
	private String title;

	@MdHeadLine(level = 3, name = "简述")
	private String description;

	@MdHeadLine(level = 3, name = "任务分类")
	private String taskType;

	@MdHeadLine(level = 3, name = "分数", parser = IntegerParser.class)
	private Integer score;

	@MdHeadLine(level = 3, name = "技能点")
	private String skillPoints;

	@MdHeadLine(level = 3, name = "题目类型", convert = QuestionTypeConvert.class)
	private Integer type;

}
