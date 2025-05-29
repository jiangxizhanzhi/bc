package com.cyberpeace.oj.course.service.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页报告博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PageReportBo` 的类，用于表示分页报告。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`PageReportBo` 
 * - **继承关系**：实现了 `Serializable` 接口 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@AllArgsConstructor`：自动生成所有字段的构造函数。 
 * - `@Getter`：为所有字段生成 getter 方法。 
 * - `@Setter`：为所有字段生成 setter 方法。 
 * ### 字段 
 * - **data**：一个类型为 `List<ClassroomReportBo>` 的列表，用于存储数据。 
 * - **total**：一个类型为 `Long` 的变量，表示总数。 
 * ### 文档注释 
 * - **类文档**：描述了该类是一个分页报告，并提供了作者、版本和日期信息。 
 * - **字段文档**：描述了 `data` 和 `total` 字段的作用和类型。 
 * ### 总结 
 * - **用途**：`PageReportBo` 类用于封装分页数据和总记录数。 
 * - **功能**：包含数据列表和总数字段，便于在分页查询中使用。 
 * - **特性**：通过 Lombok 注解简化了代码，减少了样板代码的编写。 
 * 这段代码简洁明了，适用于需要分页展示数据的场景。 
**/
@Getter
@Setter
@AllArgsConstructor
public class PageReportBo implements Serializable {

    /**
     * 数据
     *
     * @see List<ClassroomReportBo>
     */
    private List<ClassroomReportBo> data;

    /**
     * 总
     *
     * @see Long
     */
    private Long total;

}

