/**
 * 这段代码定义了一个名为 `CategoryRes` 的 Java 类，用于表示课程类别结果。以下是对其简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.res` 
 * - **类名**：`CategoryRes` 
 * - **实现接口**：`Serializable` 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：标记这个类为 Swagger API 模型对象。 
 * - `@ApiModelProperty`：描述模型属性。 
 * ### 属性 
 * - **resourceId**：表示资源编号，类型为 `String`。 
 * - **name**：表示课程分类名称，类型为 `String`。 
 * - **courseType**：表示课程类型，类型为 `Integer`。取值说明： 
 * - `0`：基础 
 * - `2`：攻击 
 * - `3`：防御 
 * ### 总结 
 * `CategoryRes` 类主要用于表示课程分类的结果数据，包含资源编号、分类名称和课程类型等属性。通过 Lombok 注解简化了 getter 和 setter 方法的编写，并且使用 Swagger 注解来增强其在 API 文档中的可读性和可用性。 
**/
package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 类别结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class CategoryRes implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课程分类名称")
    private String name;

    /**
     * 课程类型
     *
     * @see Integer
     */
    @ApiModelProperty("课程类型 0=基础 2=攻击 3=防御")
    private Integer courseType;

}
