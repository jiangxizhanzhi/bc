package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 图标上传结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LogoUploadRes` 的 Java 类，用于表示图标上传的结果。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.controller.res` 
 * - **类名**：`LogoUploadRes` 
 * - **实现接口**：`Serializable` 
 * ### 注解 
 * - **@ApiModel**：标记这个类是一个 Swagger API 模型。 
 * - **@AllArgsConstructor**：由 Lombok 自动生成所有属性的构造函数。 
 * - **@Getter** 和 **@Setter**：由 Lombok 自动生成所有属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **url**：类型为 `String`，表示可访问的路径。 
 * - **注释**：使用 `@ApiModelProperty` 标记，表示这是一个 Swagger API 文档中的模型属性，其描述为“可访问路径”。 
 * ### 总结 
 * - **用途**：该类主要用于表示图标上传后的结果，包含一个 `url` 属性，用于存储上传后的图片的访问路径。 
 * - **框架依赖**：使用了 Lombok 库来简化代码，同时使用了 Swagger 来生成 API 文档。 
 * - **功能**：主要提供了一个简单的数据传输对象（DTO），用于在控制器层和业务逻辑层之间传递图标上传的结果信息。 
 * 通过上述分析，可以看出这个类是一个典型的 DTO 类，用于封装图标上传的结果信息，并且利用了 Lombok 和 Swagger 提高开发效率和文档生成质量。 
**/
@Getter
@Setter
@ApiModel
@AllArgsConstructor
public class LogoUploadRes implements Serializable {

    /**
     * 网址
     *
     * @see String
     */
    @ApiModelProperty("可访问路径")
    private String url;

}

