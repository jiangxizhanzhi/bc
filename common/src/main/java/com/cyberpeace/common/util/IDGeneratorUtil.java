/**
 * 这段代码定义了一个工具类 `IDGeneratorUtil`，用于生成唯一标识符（ID）。以下是代码的简要分析： 
 * 1. **类设计**： 
 * - 类名为 `IDGeneratorUtil`，表示这是一个用于生成 ID 的工具类。 
 * - 使用了私有构造方法 `private IDGeneratorUtil()`，确保该类不能被实例化，符合工具类的设计原则。 
 * 2. **功能实现**： 
 * - 提供了一个静态方法 `getID()`，用于生成唯一的字符串 ID。 
 * - 使用 `UUID.randomUUID()` 生成一个标准的 UUID（通用唯一标识符），并通过 `replaceAll("-", "")` 去除 UUID 中的连字符 `-`，从而得到一个无连字符的字符串形式的 ID。 
 * 3. **用途**： 
 * - 该工具类适用于需要生成唯一字符串 ID 的场景，例如生成数据库主键、会话 ID 或其他唯一标识。 
 * 总结：这是一段简洁高效的工具类代码，封装了 UUID 的生成逻辑，并去除了不必要的连字符，方便调用者直接使用。 
**/
package com.cyberpeace.common.util;

import java.util.UUID;

/**
 * idgenerator 实用程序
 *
 * @author SN
 * @date 2024/11/08
 */
public class IDGeneratorUtil {

    /**
     * idgenerator 实用程序
     */
    private IDGeneratorUtil(){}

    /**
     * 获取id
     *
     * @return {@code String }
     */
    public static String getID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
