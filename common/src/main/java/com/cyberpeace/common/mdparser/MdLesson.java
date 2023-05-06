package com.cyberpeace.common.mdparser;

import java.io.Serializable;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;

import lombok.Data;

/**
 * 课时
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MdLesson implements Serializable {

	@MdHeadLine(level = 2, name = "课时名称")
	private String lessonName;

	@MdHeadLine(level = 3, name = "类型")
	private String type;

	@MdHeadLine(level = 3, name = "知识点")
	private String knowledge;

	@MdHeadLine(level = 3, name = "难度", convert = DifficultyConvert.class)
	private Integer difficulty;

	@MdHeadLine(level = 3, name = "学习时长")
	private String duration;

	@MdHeadLine(level = 3, name = "学习类型")
	private String compulsory;

}
