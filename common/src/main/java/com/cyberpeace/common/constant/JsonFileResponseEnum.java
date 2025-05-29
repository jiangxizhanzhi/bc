package com.cyberpeace.common.constant;


import lombok.Getter;
import org.springframework.util.ClassUtils;

/**
 * HTML 模板枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个枚举类 `JsonFileResponseEnum`，用于表示 JSON 文件的相关信息。以下是代码的简要分析： 
 * 1. **功能描述**： 
 * - 这个枚举类用于管理 JSON 文件的名称和文件路径。 
 * - 每个枚举常量代表一个特定的 JSON 文件，包含文件名 (`name`) 和完整文件路径 (`filePath`)。 
 * 2. **主要特性**： 
 * - 使用了 Lombok 的 `@Getter` 注解，自动生成 `filePath` 和 `name` 属性的 getter 方法。 
 * - 枚举构造函数接受两个参数：`name`（文件名）和 `filePath`（相对路径），并通过 `ClassUtils.getDefaultClassLoader().getResource("").getPath()` 动态生成完整的文件路径。 
 * 3. **枚举常量**： 
 * - `ATTACK`：表示 `attack.json` 文件，路径为 `static/demonstration/attack.json`。 
 * - `ENGAGE`：表示 `engage.json` 文件，路径为 `static/demonstration/engage.json`。 
 * - `DEFEND`：表示 `engage.json` 文件，路径为 `static/demonstration/defend.json`。 
 * - `USERS`：表示 `users.json` 文件，路径为 `static/demonstration/users.json`。 
 * 4. **动态路径生成**： 
 * - 使用 `ClassUtils.getDefaultClassLoader().getResource("").getPath()` 获取项目的根路径，并将其与相对路径拼接，生成完整的文件路径。 
 * 5. **适用场景**： 
 * - 该枚举类适用于需要统一管理 JSON 文件路径和名称的场景，便于在项目中通过枚举值快速获取文件信息。 
 * 总结：这是一个简洁且实用的枚举类，用于封装 JSON 文件的名称和路径，方便在项目中进行统一管理和使用。 
**/
public enum JsonFileResponseEnum {

    ATTACK("attack.json", "static/demonstration/attack.json"),

    ENGAGE("engage.json", "static/demonstration/engage.json"),
    DEFEND("engage.json", "static/demonstration/defend.json"),
    USERS("users.json", "static/demonstration/users.json"),
    ;

    /**
     * 模板路径
     *
     * @see String
     */
    @Getter
    private String filePath;

    /**
     * 模板名称
     *
     * @see String
     */
    @Getter
    private String name;

    JsonFileResponseEnum(String name, String filePath) {
        this.name = name;
        this.filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + filePath;
    }

}


