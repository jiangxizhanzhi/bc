package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * 提取基础
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ExtractBase` 的 Java 类，用于表示文件提取的基础信息。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - `ExtractBase` 类封装了文件的基本信息，包括文件名、文件路径、内容以及是否为提取后的文件内容。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 注解： 
 * - `@Getter` 和 `@Setter`：自动生成字段的 getter 和 setter 方法。 
 * - `@Builder`：允许使用构建器模式创建对象。 
 * - `@AllArgsConstructor` 和 `@NoArgsConstructor`：分别生成包含所有字段和无参的构造函数。 
 * - 使用了 Swagger 注解： 
 * - `@ApiModel`：标记该类为 Swagger 模型对象。 
 * - `@ApiModelProperty`：为每个字段提供描述。 
 * 3. **实现接口**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * 4. **字段说明**： 
 * - `fileName`：存储文件名称。 
 * - `filePath`：存储文件路径。 
 * - `content`：存储文件内容。 
 * - `isExtract`：布尔值，指示是否为提取后的文件内容。 
 * 总结：这是一个简单的数据传输对象（DTO），用于在系统中传递文件相关信息，并支持序列化和 Swagger 文档生成。 
**/
@Getter
@Setter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtractBase implements Serializable {

    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String fileName;

    /**
     * 文件路径
     */
    @ApiModelProperty("文件路径")
    private String filePath;

    /**
     * 内容
     */
    @ApiModelProperty("解压文件内容")
    private String content;

    /**
     * 是提取
     */
    @ApiModelProperty("是否是解压后文件内容 true=是 false=否")
    private Boolean isExtract;
}

