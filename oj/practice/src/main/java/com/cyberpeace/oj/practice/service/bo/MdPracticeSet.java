package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import lombok.Data;

import java.io.Serializable;

/**
 * Markdown练习设置
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class MdPracticeSet implements Serializable {

    /**
     * 名称
     */
    @MdHeadLine(level = 2, name = "题库名称")
    private String name;

    /**
     * 资源id
     */
    @MdHeadLine(level = 3, name = "题库编号")
    private String resourceId;

    /**
     * 练习类型
     */
    @MdHeadLine(level = 3, name = "题库类型", convert = PracticeSet.PracticeType.class, parser = IntegerParser.class)
    private Integer practiceType;

    /**
     * 描述
     */
    @MdHeadLine(level = 3, name = "题库说明")
    private String description;

    /**
     * 图标
     */
    @MdHeadLine(level = 3, name = "LOGO")
    private String logo;

}
