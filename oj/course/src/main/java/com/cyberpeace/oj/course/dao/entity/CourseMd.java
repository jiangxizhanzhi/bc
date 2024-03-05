package com.cyberpeace.oj.course.dao.entity;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.mdparser.parser.ListParser;
import lombok.Data;

import java.util.List;

@Data
public class CourseMd {

    @MdHeadLine(level = 2, name = "课程名称")
    private String name;

    @MdHeadLine(level = 3, name = "方向")
    private String categoryId;

    @MdHeadLine(level = 3, name = "课程编号")
    private String courseNum;

    @MdHeadLine(level = 3, name = "作者")
    private String createUserId;

    @MdHeadLine(level = 3, name = "标签", parser = ListParser.class)
    private List<String> label;

    @MdHeadLine(level = 3, name = "LOGO")
    private String logo;

    @MdHeadLine(level = 3, name = "课程难度", convert = DifficultyConvert.class)
    private Integer difficulty;

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
