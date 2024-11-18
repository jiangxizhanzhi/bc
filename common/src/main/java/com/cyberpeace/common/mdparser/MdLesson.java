package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import lombok.Data;

import java.io.Serializable;

/**
 * md 课程
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MdLesson implements Serializable {

    /**
     * 课时名称
     *
     * @see String
     */
    @MdHeadLine(level = 2, name = "课时名称")
    private String lessonName;

    /**
     * 类型
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "类型")
    private String type;

    /**
     * 知识点
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "知识点")
    private String knowledge;

    /**
     * 难度
     *
     * @see Integer
     */
    @MdHeadLine(level = 3, name = "难度", convert = DifficultyConvert.class)
    private Integer difficulty;

    /**
     * 期间
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "学习时长")
    private String duration;

    /**
     * @see String
     */
    @MdHeadLine(level = 3, name = "学习类型")
    private String compulsory;

}
