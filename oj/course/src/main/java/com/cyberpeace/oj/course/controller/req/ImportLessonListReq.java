/**
 * 这段代码定义了一个名为 `ImportLessonListReq` 的 Java 类，它用于表示导入课程列表请求的数据模型。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 
 * - **继承关系**：`ImportLessonListReq` 继承自 `PageReqBase`。 
 * - **注解**： 
 * - `@ApiModel`：表示这是一个 Swagger API 模型类。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **categoryId** (`String`)：表示课程分类的 ID。 
 * - **exceptCourse** (`String`)：表示需要排除的当前课程。 
 * ### 方法 
 * - `getCategoryId()` 和 `setCategoryId(String)`：由 Lombok 自动生成，用于获取和设置 `categoryId`。 
 * - `getExceptCourse()` 和 `setExceptCourse(String)`：由 Lombok 自动生成，用于获取和设置 `exceptCourse`。 
 * ### 用途 
 * 这个类主要用于处理导入课程列表的请求数据，包含两个主要字段：`categoryId` 和 `exceptCourse`。通过继承 `PageReqBase`，可以支持分页功能。 
 * ### 总结 
 * - **作用**：定义了导入课程列表请求的数据模型。 
 * - **特点**：使用了 Swagger 注解和 Lombok 注解来简化代码。 
 * - **继承**：从 `PageReqBase` 继承，支持分页功能。 
**/
package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 导入课列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class ImportLessonListReq extends PageReqBase {

    /**
     * 类别id
     *
     * @see String
     */
    @ApiModelProperty("课程分类")
    private String categoryId;

    /**
     * 除了课程
     *
     * @see String
     */
    @ApiModelProperty("当前课程")
    private String exceptCourse;

}
