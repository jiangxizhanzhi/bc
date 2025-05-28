/**
 * 这段代码定义了一个名为 `MdLesson` 的 Java 类，用于表示 Markdown 格式的课程信息。以下是对代码的简要分析： 
 * 1. **类的功能**： 
 * - `MdLesson` 是一个实现了 `Serializable` 接口的类，用于序列化存储或传输课程信息。 
 * - 它通过注解 `@MdHeadLine` 来描述课程的不同属性及其在 Markdown 中的标题级别和名称。 
 * 2. **类的字段**： 
 * - `lessonName`：课程名称，标记为二级标题（`level = 2`）。 
 * - `type`、`knowledge`、`compulsory`：分别表示课程类型、知识点和学习类型，均为三级标题（`level = 3`）。 
 * - `difficulty`：课程难度，同样为三级标题，并使用了 `DifficultyConvert` 转换器来处理其值。 
 * - `duration`：学习时长，标记为三级标题。 
 * 3. **注解的作用**： 
 * - `@MdHeadLine` 注解用于定义每个字段在 Markdown 中的表现形式，包括标题级别 (`level`) 和字段名称 (`name`)。 
 * - 对于 `difficulty` 字段，还指定了自定义的转换器 `DifficultyConvert`，可能用于格式化或解析难度值。 
 * 4. **设计特点**： 
 * - 使用 Lombok 的 `@Data` 注解，自动生成 getter、setter、toString 等方法，简化代码。 
 * - 通过注解方式灵活定义数据结构与 Markdown 输出的映射关系，便于后续生成或解析 Markdown 文件。 
 * 总结：该类是一个基于注解的数据模型，主要用于解析和生成 Markdown 格式的课程信息，具有良好的扩展性和灵活性。 
**/
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
