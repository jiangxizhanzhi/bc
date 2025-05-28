/**
 * 该代码定义了一个名为 `MissionTemplateRsp` 的 Java 类，用于表示任务模板的响应数据结构。以下是对其功能和特性的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装任务模板的相关信息，便于在 API 响应中传递任务数据。 
 * 2. **注解与框架支持**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化了代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，方便文档生成和接口理解。 
 * 3. **字段说明**： 
 * - `resourceId`：任务的唯一标识符。 
 * - `title`：任务的标题。 
 * - `description`：任务的详细描述。 
 * - `score`：任务的分值。 
 * - `skillPoints`：任务涉及的技能点列表。 
 * - `answer`：任务的标准答案。 
 * - `option`：选择题的选项，非选择题则为 `"null"`。 
 * - `type`：任务的类型（如单选、填空等）。 
 * - `category`：任务的类别。 
 * - `writeUp`：任务的题解。 
 * 4. **设计特点**： 
 * - 属性全面且清晰，涵盖了任务模板的主要信息。 
 * - 使用 Lombok 和 Swagger 注解提升了开发效率和代码可读性。 
 * 总体而言，这是一个简洁且功能明确的数据传输对象（DTO），适合在前后端交互中使用。 
**/
package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 任务模板响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionTemplateRsp {
    /**
     * 资源id
     */
    @ApiModelProperty("任务id")
    private String resourceId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 得分
     */
    @ApiModelProperty(value = "分值")
    private Integer score;

    /**
     * 技能点
     */
    @ApiModelProperty(value = "技能点")
    private List<Integer> skillPoints;

    /**
     * 答案
     */
    @ApiModelProperty(value = "标准答案")
    private String answer;

    /**
     * 选择
     */
    @ApiModelProperty(value = "选择题选项，非选择题为\"null\"")
    private String option;

    /**
     * 类型
     */
    @ApiModelProperty(value = "任务类型（单选、填空等）")
    private Integer type;

    /**
     * 类别
     */
    @ApiModelProperty(value = "任务类别（resource_id）")
    private String category;

    /**
     * 写入向上
     */
    @ApiModelProperty(value = "题解")
    private String writeUp;

}
