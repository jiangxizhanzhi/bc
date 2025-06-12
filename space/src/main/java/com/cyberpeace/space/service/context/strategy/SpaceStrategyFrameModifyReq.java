package com.cyberpeace.space.service.context.strategy;

import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 空间策略框架修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceStrategyFrameModifyReq` 的 Java 类，用于表示空间策略框架修改请求体。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装空间策略框架修改请求所需的数据。 
 * - 它包含两个字段：`resourceId` 和 `name`，分别表示资源 ID 和名称。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - `@ApiModelProperty` 是 Swagger 提供的注解，用于描述 API 接口中的字段信息（此处未提供具体描述）。 
 * 3. **字段说明**： 
 * - `resourceId`：表示资源的唯一标识符。 
 * - `name`：表示与资源相关的名称。 
 * 4. **设计特点**： 
 * - 代码简洁，符合面向对象的设计原则。 
 * - 使用了 Lombok 和 Swagger 注解，提升了开发效率并增强了代码的可读性。 
 * 总结：这是一个简单的数据传输对象（DTO），用于传递空间策略框架修改的相关信息。 
**/
@Getter
@Setter
public class SpaceStrategyFrameModifyReq{
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty(value = "")
    private String name;
}

