package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.convert.SceneShareConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * md ctf 基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class MdCtfBase implements Serializable {

	/**
	 * 标题
	 *
	 * @see String
	 */
	@MdHeadLine(level = 2, name = "标题")
	private String title;

	/**
	 * 作者
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "作者")
	private String author;

	/**
	 * 类别
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "方向")
	private String category;

	/**
	 * 知识
	 *
	 * @see List<String>
	 */
	@MdHeadLine(level = 3, name = "知识点", parser = ListParser.class)
	private List<String> knowledge;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "难度", convert = DifficultyConvert.class)
	private Integer difficulty;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "内容")
	private String content;

	/**
	 * 技巧
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "提示")
	private String tips;

	/**
	 * 场景共享模式
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "是否可共享", parser = IntegerParser.class,
			convert = SceneShareConvert.class)
	private Integer sceneShareMode;

	/**
	 * 备注
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "备注")
	private String remark;

}
