package com.cyberpeace.oj.practice.service.md;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import lombok.Data;

import java.util.List;

/**
 * markdown理论练习
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MdTheoryPractice {

	/**
	 * 课后习题
	 */
	@MdHeadLine(level = 1, name = "课后习题")
	private String title;

	/**
	 * 单选题
	 */
	@MdHeadLine(level = 2, name = "单选题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> singleList;

	/**
	 * 多选题
	 */
	@MdHeadLine(level = 2, name = "多选题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> multipleList;

	/**
	 * 填空题
	 */
	@MdHeadLine(level = 2, name = "填空题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> blankList;

	/**
	 * 简答题
	 */
	@MdHeadLine(level = 2, name = "简答题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> anwserList;

	/**
	 * 判断题
	 */
	@MdHeadLine(level = 2, name = "判断题", parser = BaseMdParser.class)
	private List<TheoryBaseMd> judgeList;
}
