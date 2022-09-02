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
 */
@Data
public class MultipleChoiceMD implements Serializable {
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

	@MdHeadLine(level = 3, name = "题目选项", parser = ListParser.class)
	private List<String> options;

	@MdHeadLine(level = 3, name = "答案", parser = MultipleChoiceParser.class)
	private String answer;

}
