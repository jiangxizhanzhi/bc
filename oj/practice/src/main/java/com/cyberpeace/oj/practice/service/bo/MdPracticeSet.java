package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import com.cyberpeace.common.mdparser.parser.IntegerParser;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import lombok.Data;

import java.io.Serializable;

/**
 * Markdown实践设置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MdPracticeSet implements Serializable {

    /**
     * 名称
     *
     * @see String
     */
    @MdHeadLine(level = 2, name = "题库名称")
    private String name;

    /**
     * 资源id
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "题库编号")
    private String resourceId;

    /**
     * 执业类型
     *
     * @see Integer
     */
    @MdHeadLine(level = 3, name = "题库类型", convert = PracticeSet.PracticeType.class, parser = IntegerParser.class)
    private Integer practiceType;

    /**
     * 描述
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "题库说明")
    private String description;

    /**
     * 图标
     *
     * @see String
     */
    @MdHeadLine(level = 3, name = "LOGO")
    private String logo;

}
