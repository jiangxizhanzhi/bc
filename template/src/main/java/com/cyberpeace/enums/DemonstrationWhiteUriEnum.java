/**
 * 该代码定义了一个枚举类 `DemonstrationWhiteUriEnum`，用于表示演示白色 URI 的相关路径信息。以下是代码的简要分析： 
 * 1. **功能概述**： 
 * - 该枚举类主要用于存储与“演示白色 URI”相关的两个路径信息：文件路径 (`filePath`) 和系统路径 (`systemPath`)。 
 * 2. **枚举常量**： 
 * - 定义了一个枚举常量 `WHITE`，它有两个参数： 
 * - `"/cpms/demonstration/demonstrationWhite.json"`：文件路径。 
 * - `"classpath:static/demonstration/demonstrationWhite.json"`：系统路径。 
 * 3. **字段**： 
 * - `private String filePath;`：存储文件路径。 
 * - `private String systemPath;`：存储系统路径。 
 * 4. **构造方法**： 
 * - 枚举类的构造方法接收两个字符串参数，分别初始化 `filePath` 和 `systemPath`。 
 * 5. **Getter 方法**： 
 * - 提供了两个公共方法 `getFilePath()` 和 `getSystemPath()`，用于获取对应的路径信息。 
 * 6. **注释**： 
 * - 代码中有详细的注释，说明了每个部分的功能和用途，包括枚举常量、字段和方法。 
 * 总结：这是一个简单的枚举类，用于管理与演示白色 URI 相关的路径信息，具有良好的可读性和结构化设计。 
**/
package com.cyberpeace.enums;


/**
 * 演示白色 URI 枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Enum
 * @since 1.0.0
 */
public enum DemonstrationWhiteUriEnum {
    /**
     * 白
     *
     * @see DemonstrationWhiteUriEnum
     */
    WHITE("/cpms/demonstration/demonstrationWhite.json","classpath:static/demonstration/demonstrationWhite.json");


    /**
     * 文件路径
     *
     * @see String
     */
    private String filePath;

    /**
     * 系统路径
     *
     * @see String
     */
    private String systemPath;


    /**
     * 演示白色 URI 枚举
     *
     * @param filePath   文件路径
     * @param systemPath 系统路径
     * @throws
     * @see String
     * @see String
     */
    DemonstrationWhiteUriEnum(String filePath, String systemPath) {
        this.filePath = filePath;
        this.systemPath = systemPath;
    }


    /**
     * 获取文件路径
     *
     * @return 字符串
     * @throws
     * @see String
     */
    public String getFilePath() {
        return filePath;
    }


    /**
     * 获取系统路径
     *
     * @return 字符串
     * @throws
     * @see String
     */
    public String getSystemPath() {
        return systemPath;
    }

}
