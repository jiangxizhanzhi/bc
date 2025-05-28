/**
 * 这段代码定义了一个名为 `IndexTraffic` 的 Java 类，用于表示编号流量相关的数据模型。以下是代码的简要分析： 
 * 1. **类的用途**： 
 * - 该类主要用于存储与编号流量相关的四种流量数据：协议流量、用户行为流量、恶意攻击流量以及总流量。 
 * 2. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述信息，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `protocol`：表示协议流量，默认值为 0。 
 * - `behavior`：表示用户行为流量，默认值为 0。 
 * - `attack`：表示恶意攻击流量，默认值为 0。 
 * - `sum`：表示总流量，默认值为 0。 
 * 4. **设计特点**： 
 * - 所有字段均为 `Integer` 类型，并且都有默认值，方便初始化时无需手动赋值。 
 * - 通过注解增强了代码的可读性和可维护性，特别是与 Swagger 集成后，便于生成清晰的 API 文档。 
 * 总结：这是一个简单但功能明确的数据模型类，适用于存储和管理编号流量相关的信息。 
**/
package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号流量
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexTraffic {

    /**
     * 协议
     *
     * @see Integer
     */
    @ApiModelProperty(value = "协议流量")
    private Integer protocol = 0;

    /**
     * 行为
     *
     * @see Integer
     */
    @ApiModelProperty(value = "用户行为流量")
    private Integer behavior = 0;

    /**
     * 攻击
     *
     * @see Integer
     */
    @ApiModelProperty(value = "恶意攻击流量:")
    private Integer attack = 0;

    /**
     * 和
     *
     * @see Integer
     */
    @ApiModelProperty(value = "")
    private Integer sum = 0;

}
