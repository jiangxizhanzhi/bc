package com.cyberpeace.cr.tool.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Web工具类别响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `WebToolCategoryRsp` 的 Java 类，用于表示前端工具类别的响应数据。以下是对其主要特性的简要分析： 
 * 1. **包声明与导入**: 
 * - 该类位于 `com.cyberpeace.cr.tool.controller.res` 包中。 
 * - 导入了 Lombok 注解库、Swagger 注解以及 `Serializable` 接口。 
 * 2. **注解**: 
 * - `@ApiModel("前台工具分类")`: 说明这个类是 Swagger 文档中的一个模型，并给它起名为“前台工具分类”。 
 * - `@Getter` 和 `@Setter`: Lombok 注解，自动生成所有字段的 getter 和 setter 方法。 
 * - `@Builder`: Lombok 注解，允许使用构建器模式来创建对象实例。 
 * - `implements Serializable`: 表示该类可以被序列化，通常用于在网络传输或持久化时保存对象状态。 
 * 3. **字段**: 
 * - `String resourceId`: 表示资源 ID。 
 * - `String categoryI18nName`: 表示工具类别的国际化名称。使用了 `@ApiModelProperty` 注解来描述该字段在 Swagger 文档中的描述信息。 
 * 4. **功能**: 
 * - 该类主要用于在前端工具分类相关的 API 响应中传递数据。 
 * - 通过 Swagger 注解，可以在文档中清晰地看到该类包含哪些字段及其描述信息。 
 * - 使用 Lombok 注解简化了代码，减少了样板代码。 
 * 总结来说，这段代码是一个简洁且功能明确的数据传输对象（DTO），适用于在前后端交互中传递工具分类的相关信息。 
**/
@ApiModel("前台工具分类")
@Getter
@Setter
@Builder
public class WebToolCategoryRsp implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 类别i18n名称
     */
    @ApiModelProperty("工具分类名称")
    private String categoryI18nName;
}

