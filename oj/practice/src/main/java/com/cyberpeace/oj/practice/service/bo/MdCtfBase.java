package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.convert.SceneShareConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.common.mdparser.parser.ListParser;

import lombok.Data;

/**
 * markdownCTF基础
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class MdCtfBase implements Serializable {

	/**
	 * 标题
	 */
	@MdHeadLine(level = 2, name = "标题")
	private String title;

	/**
	 * 作者
	 */
	@MdHeadLine(level = 3, name = "作者")
	private String author;

	/**
	 * 类别
	 */
	@MdHeadLine(level = 3, name = "方向")
	private String category;

	/**
	 * 知识点
	 */
	@MdHeadLine(level = 3, name = "知识点", parser = ListParser.class)
	private List<String> knowledge;

	/**
	 * 难度
	 */
	@MdHeadLine(level = 3, name = "难度", convert = DifficultyConvert.class)
	private Integer difficulty;

	/**
	 * 内容
	 */
	@MdHeadLine(level = 3, name = "内容")
	private String content;

	/**
	 * 技巧
	 */
	@MdHeadLine(level = 3, name = "提示")
	private String tips;

	/**
	 * 场景共享模式
	 */
	@MdHeadLine(level = 3, name = "是否可共享", parser = IntegerParser.class,
			convert = SceneShareConvert.class)
	private Integer sceneShareMode;

	/**
	 * 备注
	 */
	@MdHeadLine(level = 3, name = "备注")
	private String remark;

}
