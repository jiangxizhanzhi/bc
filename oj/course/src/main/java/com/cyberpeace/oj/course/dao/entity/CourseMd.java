package com.cyberpeace.oj.course.dao.entity;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Data;

import java.util.List;

/**
 * 课程 MD
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Data
public class CourseMd {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@MdHeadLine(level = 2, name = "课程名称")
	private String name;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "方向")
	private String categoryId;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "课程编号")
	private String courseNum;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "作者")
	private String createUserId;

	/**
	 * 标签
	 *
	 * @see List<String>
	 */
	@MdHeadLine(level = 3, name = "标签", parser = ListParser.class)
	private List<String> label;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "LOGO")
	private String logo;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@MdHeadLine(level = 3, name = "课程难度", convert = DifficultyConvert.class)
	private Integer difficulty;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@MdHeadLine(level = 3, name = "简介")
	private String description;

	public void setLogo(String logo) {
		if(null != logo){
			String[] split = logo.split("\\.");
			if(split.length > 0){
				logo = "logo." + split[split.length - 1];
			}

		}
		this.logo = "![](" + logo + ")";
	}
}
