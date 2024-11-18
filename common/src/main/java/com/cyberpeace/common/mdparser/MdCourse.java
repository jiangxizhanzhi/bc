package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Markdown课程
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MdCourse implements Serializable {

    /**
     * 课程名称
     *
     * @see String
     */
    @MdHeadLine(level = 2, name = "课程名称")
    private String courseName;

    /**
     * 类别
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "方向")
    private String category;

    /**
     * 课程数量
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "课程编号")
    private String courseNum;

    /**
     * 作者
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "作者")
    private String author;

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

}
