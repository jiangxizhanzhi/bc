package com.cyberpeace.common.constant;

/**
 * md 常数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `MdConstant` 的 Java 接口，用于存储与 Markdown 格式相关的常量。以下是简要分析： 
 * 1. **接口作用**： 
 * - 该接口的主要目的是提供一组与 Markdown 表达式相关的常量，便于在项目中统一使用。 
 * 2. **常量说明**： 
 * - `ONLY_URL_IMG`：表示一种仅包含图片 URL 的 Markdown 表达式，格式为 `![](%s)`，其中 `%s` 是占位符，用于动态插入图片 URL。 
 * - `IMG`：表示完整的 Markdown 图片语法，格式为 `![%s](%s)`，第一个 `%s` 是图片描述，第二个 `%s` 是图片 URL。 
 * - `ONLY_DESCRIPTION_IMG`：表示仅有描述但没有 URL 的 Markdown 图片语法，格式为 `![%s]()`，其中 `%s` 是图片描述。 
 * 3. **设计特点**： 
 * - 所有常量均为 `String` 类型，且通过接口定义，确保其不可变性。 
 * - 注释清晰地描述了每个常量的作用及使用场景，方便开发者理解。 
 * 4. **适用场景**： 
 * - 这些常量适用于需要生成或解析 Markdown 文本的场景，特别是在处理图片相关的语法时。 
 * 总结：这是一个简单的工具类接口，提供了常用的 Markdown 图片语法常量，有助于代码的可读性和复用性。 
**/
public interface MdConstant {

    /**
     * 仅 URL img
     *
     * @see String
     */
    String ONLY_URL_IMG = "![](%s)";

    /**
     * IMG
     *
     * @see String
     */
    String IMG = "![%s](%s)";

    /**
     * 仅描述 IMG
     *
     * @see String
     */
    String ONLY_DESCRIPTION_IMG = "![%s]()";

}

