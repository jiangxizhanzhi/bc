package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 直方图结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `HistogramRes` 的 Java 类，用于表示直方图的结果。以下是对该代码的简要分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.oj.course.controller.res; 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * import java.util.List; 
 * ``` 
 * - `com.cyberpeace.oj.course.controller.res`: 这是类所在的包。 
 * - 导入了 `ApiModel` 和 `ApiModelProperty` 注解，这些注解来自 Swagger，用于生成 API 文档。 
 * - `@Getter` 和 `@Setter` 注解来自 Lombok，用于自动生成 getter 和 setter 方法。 
 * - `Serializable` 接口用于实现序列化。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * @ApiModel("进度数据") 
 * public class HistogramRes implements Serializable { 
 * ``` 
 * - `@Getter` 和 `@Setter`：使用 Lombok 自动生成 getter 和 setter 方法。 
 * - `@ApiModel("进度数据")`：使用 Swagger 注解来描述这个类。 
 * - `implements Serializable`：表示这个类可以被序列化。 
 * ### 成员变量 
 * ```java 
 * /** 
 * * x 个名称 
 * * 
 * * @see List<String> 
 * *
 * @ApiModelProperty("x轴，统计项") 
 * private List<String> xNames; 
 * /** 
 * * y 值 
 * * 
 * * @see List<Integer> 
 * *
 * @ApiModelProperty("y轴，柱形值") 
 * private List<Integer> yValues; 
 * ``` 
 * - `xNames`：一个 `List<String>` 类型的变量，用于存储直方图 X 轴的名称。 
 * - `yValues`：一个 `List<Integer>` 类型的变量，用于存储直方图 Y 轴的值。 
 * - `@ApiModelProperty` 注解用于在 Swagger 文档中描述这些字段。 
 * ### 总结 
 * - **用途**：`HistogramRes` 类用于表示直方图的数据结构，包含 X 轴名称和 Y 轴值。 
 * - **特性**： 
 * - 使用 Lombok 简化了 getter 和 setter 方法的编写。 
 * - 使用 Swagger 注解生成 API 文档。 
 * - 实现了 `Serializable` 接口，允许对象被序列化。 
 * 这个类是一个典型的 POJO（Plain Old Java Object），用于封装直方图数据，并且通过注解增强了其可读性和文档生成能力。 
**/
@Getter
@Setter
@ApiModel("进度数据")
public class HistogramRes implements Serializable {

    /**
     * x 个名称
     *
     * @see List<String>
     */
    @ApiModelProperty("x轴，统计项")
    private List<String> xNames;

    /**
     * y 值
     *
     * @see List<Integer>
     */
    @ApiModelProperty("y轴，柱形值")
    private List<Integer> yValues;

}

