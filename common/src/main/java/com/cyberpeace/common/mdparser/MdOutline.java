package com.cyberpeace.common.mdparser;

import com.cyberpeace.common.mdparser.annotations.MdHeadLine;
import lombok.Data;

import java.io.Serializable;

/**
 * MD 大纲
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MdOutline` 的 Java 类，用于表示 Markdown 文件的大纲。以下是代码的简要分析： 
 * 1. **类定义**： 
 * - 类名为 `MdOutline`，位于包 `com.cyberpeace.common.mdparser` 中。 
 * - 使用了 Lombok 的 `@Data` 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * 2. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 3. **字段**： 
 * - 定义了一个私有字符串字段 `outline`，用于存储课时大纲内容。 
 * 4. **注解**： 
 * - 使用了自定义注解 `@MdHeadLine`，为字段 `outline` 添加了元数据： 
 * - `level = 2`：表示大纲的级别为二级。 
 * - `name = "课时大纲"`：指定字段的名称为“课时大纲”。 
 * 5. **用途**： 
 * - 该类主要用于解析和存储 Markdown 文件中的大纲信息，通过注解 `@MdHeadLine` 提供额外的结构化信息。 
 * 总结：`MdOutline` 是一个简单的数据传输对象（DTO），结合注解用于描述 Markdown 文件的大纲内容。 
**/
@Data
public class MdOutline implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    @MdHeadLine(level = 2, name = "课时大纲")
    private String outline;

}

