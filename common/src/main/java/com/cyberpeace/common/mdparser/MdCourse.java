package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 医学博士课程
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MdCourse implements Serializable {

    @MdHeadLine(level = 2, name = "课程名称")
    private String courseName;

    @MdHeadLine(level = 3, name = "方向")
    private String category;

    @MdHeadLine(level = 3, name = "课程编号")
    private String courseNum;

    @MdHeadLine(level = 3, name = "作者")
    private String author;

    @MdHeadLine(level = 3, name = "标签", parser = ListParser.class)
    private List<String> label;

    @MdHeadLine(level = 3, name = "LOGO")
    private String logo;

    @MdHeadLine(level = 3, name = "课程难度", convert = DifficultyConvert.class)
    private Integer difficulty;

    @MdHeadLine(level = 3, name = "简介")
    private String description;

}
