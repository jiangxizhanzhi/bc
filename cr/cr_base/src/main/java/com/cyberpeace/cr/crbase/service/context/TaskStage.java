package com.cyberpeace.cr.crbase.service.context;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务阶段
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `TaskStage` 的类，它是对 `CrTask` 实体类的扩展。以下是代码的简要分析： 
 * 1. **继承关系**：`TaskStage` 类继承自 `CrTask`，这意味着它包含了 `CrTask` 中的所有属性和方法，并可以添加新的字段或功能。 
 * 2. **Lombok 注解**：使用了 `@Getter` 和 `@Setter` 注解，自动生成了所有字段的 getter 和 setter 方法，简化了代码量并提高了开发效率。 
 * 3. **新增字段**： 
 * - `stage`：表示任务的阶段。 
 * - `name`：任务的名称。 
 * - `description`：任务的描述，带有 Swagger 注解，用于生成 API 文档。 
 * - `writeUp`：题解内容。 
 * - `showWriteup`：布尔值，指示是否显示题解。 
 * 4. **Swagger 注解**：`@ApiModelProperty` 用于为字段提供元数据，帮助生成清晰的 API 文档。 
 * 总结：`TaskStage` 类是对 `CrTask` 的扩展，增加了与任务阶段相关的特定字段，适用于更复杂的业务场景，同时通过 Lombok 和 Swagger 注解提升了代码的可维护性和文档生成的便利性。 
**/
@Getter
@Setter
public class TaskStage extends CrTask {

    /**
     * 阶段
     */
    private String stage;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty(value = "任务描述")
    private String description;

    /**
     * 写入向上
     */
    @ApiModelProperty(value = "题解")
    private String writeUp;

    /**
     * 显示 Writeup
     */
    @ApiModelProperty(value = "是否显示题解")
    private Boolean showWriteup;
}

