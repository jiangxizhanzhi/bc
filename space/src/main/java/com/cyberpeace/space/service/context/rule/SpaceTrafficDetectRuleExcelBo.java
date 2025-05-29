package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间流量检测统治胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceTrafficDetectRuleExcelBo` 的 Java 类，用于表示空间流量检测规则的相关信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类是一个数据传输对象（DTO），用于存储与空间流量检测规则相关的信息。 
 * - 它主要用于 Excel 数据的读取和写入，因此命名为 `ExcelBo`。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供描述性信息，便于 API 文档生成。 
 * 3. **字段说明**： 
 * - `ruleContent`：规则内容，描述具体规则的文本。 
 * - `ruleId`：规则编号，唯一标识一条规则。 
 * - `ruleInfo`：规则信息，提供更多关于规则的详细描述。 
 * - `threatLevel`：威胁等级，表示规则对应的威胁程度。 
 * - `tool`：工具，可能指用于执行该规则的工具或方法。 
 * 4. **设计特点**： 
 * - 类的设计简洁明了，专注于存储和传输数据。 
 * - 使用了现代 Java 特性（如 Lombok 和 Swagger 注解），减少了样板代码，提高了开发效率。 
 * 总结：这是一个简单的 DTO 类，用于封装空间流量检测规则的相关信息，适合在服务层和外部接口之间传递数据。 
**/
@Getter
@Setter
public class SpaceTrafficDetectRuleExcelBo {

    /**
     * 规则内容
     *
     * @see String
     */
    @ApiModelProperty(value = "规则内容")
    private String ruleContent;

    /**
     * 统治id
     *
     * @see String
     */
    @ApiModelProperty(value = "规则编号")
    private String ruleId;

    /**
     * 规则信息
     *
     * @see String
     */
    @ApiModelProperty(value = "规则信息")
    private String ruleInfo;

    /**
     * 威胁层级
     *
     * @see String
     */
    @ApiModelProperty(value = "威胁等级")
    private String threatLevel;

    /**
     * 工具
     *
     * @see String
     */
    @ApiModelProperty(value = "工具")
    private String tool;

    @ApiModelProperty(value = "攻击类型")
    private String attackType;
}

