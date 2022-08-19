package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import lombok.Data;

import java.io.Serializable;

@Data
public class MdPracticeSet implements Serializable {

    @MdHeadLine(level = 2, name = "题库名称")
    private String name;

    @MdHeadLine(level = 3, name = "题库编号")
    private String resourceId;

    @MdHeadLine(level = 3, name = "题库类型", convert = PracticeSet.PracticeType.class, parser = IntegerParser.class)
    private Integer practiceType;

    @MdHeadLine(level = 3, name = "题库说明")
    private String description;

    @MdHeadLine(level = 3, name = "LOGO")
    private String logo;

}
