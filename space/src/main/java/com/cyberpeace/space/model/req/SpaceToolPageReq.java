package com.cyberpeace.space.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 这段代码定义了一个名为 `SpaceToolPageReq` 的 Java 类，它是基于 `SpacePageReqBase` 的一个扩展类。以下是对代码的简要分析： 
 * 1. **包与导入**： 
 * - 该类位于 `com.cyberpeace.space.model.req` 包中。 
 * - 使用了 `io.swagger.annotations` 包中的注解，用于生成 API 文档。 
 * - 引入了 Lombok 的 `@Getter` 和 `@Setter` 注解，以自动生成 getter 和 setter 方法。 
 * 2. **类注解**： 
 * - `@ApiModel`：标记该类为一个模型类，通常用于 Swagger 文档中描述 API 请求或响应模型。 
 * 3. **字段与注解**： 
 * - `tool` 字段：用于存储工具搜索的关键字，带有 `@ApiModelProperty` 注解，描述其在 API 文档中的含义。 
 * - `stage` 字段：表示某个阶段的整数值，同样带有 `@ApiModelProperty` 注解。 
 * 4. **继承**： 
 * - 该类继承自 `SpacePageReqBase`，可能包含一些通用的分页或其他基础属性。 
 * 总结：这是一个用于分页查询工具信息的请求类，支持通过工具名称和阶段进行筛选，并且利用了 Swagger 和 Lombok 来简化开发和文档生成。 
**/
@ApiModel
public class SpaceToolPageReq extends SpacePageReqBase {

    @Getter
    @Setter
    @ApiModelProperty(value = "工具搜索")
	private String tool;

    @Getter
    @Setter
    @ApiModelProperty(value = "阶段")
    private Integer stage;

}

