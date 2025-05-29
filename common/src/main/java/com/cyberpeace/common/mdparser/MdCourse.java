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
 * @date 2024/11/08
 */
/**
 * 该代码定义了一个名为 `MdCourse` 的 Java 类，用于表示 Markdown 格式的课程信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `MdCourse` 是一个实现了 `Serializable` 接口的类，用于存储和管理 Markdown 课程的相关信息。 
 * 2. **注解的使用**： 
 * - 使用了自定义注解 `@MdHeadLine` 来描述每个字段在 Markdown 中的标题级别 (`level`) 和名称 (`name`)。 
 * - 部分字段还指定了特殊的解析器或转换器： 
 * - `label` 字段使用了 `ListParser` 解析器来处理列表数据。 
 * - `difficulty` 字段使用了 `DifficultyConvert` 转换器来处理难度值。 
 * 3. **字段说明**： 
 * - `courseName`: 课程名称，标题级别为 2。 
 * - `category`: 课程方向，标题级别为 3。 
 * - `courseNum`: 课程编号，标题级别为 3。 
 * - `author`: 课程作者，标题级别为 3。 
 * - `label`: 课程标签，标题级别为 3，使用 `ListParser` 解析。 
 * - `logo`: 课程 LOGO 图片链接，标题级别为 3。 
 * - `difficulty`: 课程难度，标题级别为 3，使用 `DifficultyConvert` 转换。 
 * - `description`: 课程简介，标题级别为 3。 
 * 4. **Lombok 注解**： 
 * - 使用了 `@Data` 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法，简化了代码。 
 * 5. **用途**： 
 * - 该类主要用于将 Markdown 格式的课程信息映射到 Java 对象，便于后续处理和展示。 
 * 总结：`MdCourse` 是一个基于注解驱动的数据模型类，用于解析和存储 Markdown 格式的课程信息，支持灵活的字段配置和数据转换。 
**/
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

