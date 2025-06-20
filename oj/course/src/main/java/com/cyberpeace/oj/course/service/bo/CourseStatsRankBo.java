package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程统计 排名 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseStatsRankBo` 的 Java 类，该类用于表示课程学习排名的相关信息。以下是对其主要特性和功能的简要分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 表示这个类位于 `com.cyberpeace.oj.course.service.bo` 包下。 
 * - **导入**： 
 * - `com.alibaba.fastjson.JSONObject`：使用 FastJSON 库中的 `JSONObject` 来存储 JSON 数据。 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：这两个注解用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成类属性的 getter 和 setter 方法。 
 * - `@ApiModel("课程学习排名")`：用于描述这个类在 Swagger 文档中显示的名称。 
 * - 实现 `Serializable` 接口，使得这个类的对象可以被序列化。 
 * ### 类属性 
 * - `private JSONObject peopleOrderingData;` 
 * - 使用 `@ApiModelProperty("课程学习排名")` 注解，说明这个字段在 Swagger 文档中的描述。 
 * - 存储订购课程数据的人员信息。 
 * - `private JSONObject directionOrderingData;` 
 * - 使用 `@ApiModelProperty("课程方向学习排名")` 注解，说明这个字段在 Swagger 文档中的描述。 
 * - 存储订购课程方向的数据。 
 * ### 总结 
 * - 这个类主要用于封装课程学习排名的信息，包括订购课程的人员数据和课程方向的数据。 
 * - 通过 Lombok 的注解简化了代码，减少了手动编写 getter 和 setter 方法的工作量。 
 * - 使用 Swagger 注解来帮助生成文档，便于其他开发者理解和使用这个类。 
**/
@Getter
@Setter
@ApiModel("课程学习排名")
public class CourseStatsRankBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * 订购数据人员
     *
     * @see JSONObject
     */
    @ApiModelProperty("课程学习排名")
    private JSONObject peopleOrderingData;

    /**
     * 方向订购数据
     *
     * @see JSONObject
     */
    @ApiModelProperty("课程方向学习排名")
    private JSONObject directionOrderingData;

}

