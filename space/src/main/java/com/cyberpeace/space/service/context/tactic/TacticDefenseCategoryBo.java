package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 战法防御类别Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `TacticDefenseCategoryBo` 的 Java 类，属于 `com.cyberpeace.space.service.context.tactic` 包。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 这是一个 BO（Business Object）类，用于表示战法防御类别相关的业务数据。 
 * - 它包含了与防御类别相关的一些基本信息字段。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成类的 getter 和 setter 方法，简化了代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，用于描述字段 `stage` 的元信息（虽然 `value` 字段为空）。 
 * 3. **字段说明**： 
 * - `resourceId`：资源 ID，唯一标识某个资源。 
 * - `name`：名称，表示防御类别的名字。 
 * - `stage`：阶段，可能表示防御类别的不同阶段或层次。 
 * - `cover`：封面，可能是某种图像或链接，用于展示防御类别的封面。 
 * 4. **设计目的**： 
 * - 该类主要用于封装战法防御类别相关的数据，便于在系统中传递和使用。 
 * 总体来说，这是一个简单的数据传输对象（DTO），用于存储和传输防御类别的基本信息。 
**/
@Getter
@Setter
public class TacticDefenseCategoryBo {
    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 阶段
     */
    @ApiModelProperty(value = "")
    private Integer stage;

    /**
     * 封面
     */
    private String cover;
}

