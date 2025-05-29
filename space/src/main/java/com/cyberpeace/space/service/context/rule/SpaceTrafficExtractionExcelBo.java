package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间流量萃取胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceTrafficExtractionExcelBo` 的 Java 类，位于包 `com.cyberpeace.space.service.context.rule` 下。以下是对其功能和结构的简要分析： 
 * ### 功能描述 
 * 该类是一个用于表示“空间流量萃取胜过 Bo”（可能是一个业务对象）的数据传输对象（DTO）。它包含了与空间流量萃取相关的属性，并通过注解和 Lombok 提供了 getter 和 setter 方法。 
 * ### 主要特点 
 * 1. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了元信息（如字段名），便于生成 API 文档。 
 * 2. **属性说明**： 
 * - `name`：表示名称，类型为 `String`。 
 * - `order`：表示次序或顺序，类型为 `String`。 
 * - `content`：表示内容，类型为 `String`。 
 * - `tool`：表示工具，类型为 `String`。 
 * 3. **文档注释**： 
 * - 类和字段都有详细的 Javadoc 注释，清晰地描述了类的用途和字段的含义。 
 * ### 总结 
 * 这是一个简单的数据传输对象（DTO），主要用于封装与空间流量萃取相关的信息。通过 Lombok 和 Swagger 注解，减少了样板代码，提高了开发效率。 
**/
@Getter
@Setter
public class SpaceTrafficExtractionExcelBo {

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 次序
     *
     * @see String
     */
    @ApiModelProperty(value = "顺序")
    private String order;

    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 工具
     *
     * @see String
     */
    @ApiModelProperty(value = "工具")
    private String tool;


}

