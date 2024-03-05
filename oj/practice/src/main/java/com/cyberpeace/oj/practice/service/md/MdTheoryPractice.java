package com.cyberpeace.oj.practice.service.md;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import lombok.Data;

import java.util.List;

@Data
public class MdTheoryPractice {

	@MdHeadLine(level = 1, name = "课后习题")
	private String title;

	@MdHeadLine(level = 2, name = "单选题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> singleList;

	@MdHeadLine(level = 2, name = "多选题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> multipleList;

	@MdHeadLine(level = 2, name = "填空题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> blankList;

	@MdHeadLine(level = 2, name = "简答题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> anwserList;

	@MdHeadLine(level = 2, name = "判断题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> judgeList;
}
