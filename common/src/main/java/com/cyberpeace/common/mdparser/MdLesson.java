package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import lombok.Data;

import java.io.Serializable;

/**
 * md 课程
 *
 * @author SN
 * @date 2024/11/08
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
