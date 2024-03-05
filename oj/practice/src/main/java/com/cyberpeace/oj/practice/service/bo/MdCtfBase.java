package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.convert.SceneShareConvert;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.common.mdparser.parser.ListParser;

import lombok.Data;

@Data
public class MdCtfBase implements Serializable {

    @MdHeadLine(level = 2, name = "标题")
    private String title;

    @MdHeadLine(level = 3, name = "作者")
    private String author;

    @MdHeadLine(level = 3, name = "方向")
    private String category;

    @MdHeadLine(level = 3, name = "知识点", parser = ListParser.class)
    private List<String> knowledge;

    @MdHeadLine(level = 3, name = "难度", convert = DifficultyConvert.class)
    private Integer difficulty;

    @MdHeadLine(level = 3, name = "内容")
    private String content;

    @MdHeadLine(level = 3, name = "提示")
    private String tips;

    @MdHeadLine(level = 3, name = "是否可共享", parser = IntegerParser.class,
            convert = SceneShareConvert.class)
    private Integer sceneShareMode;

    @MdHeadLine(level = 3, name = "备注")
    private String remark;

}
