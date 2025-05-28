/**
 * 这段代码定义了一个名为 `RMImportReq` 的 Java 类，该类用于表示某种导入请求的数据结构。以下是对其主要特性的分析： 
 * 1. **包声明**： 
 * - 这个类位于 `com.cyberpeace.oj.ojbase.controller.req` 包中。 
 * 2. **导入的库**： 
 * - 使用了 `io.swagger.annotations` 库来生成API文档注解。 
 * - 使用了 `lombok` 库来简化类的创建（如 `@Getter`, `@Setter`）。 
 * - 使用了 `javax.validation.constraints.NotBlank` 来进行验证。 
 * 3. **类定义**： 
 * - `RMImportReq` 类实现了 `Serializable` 接口，表明这个类的对象可以被序列化，通常用于网络传输或存储。 
 * 4. **类属性**： 
 * - `path`：一个字符串类型的属性，用于存储资源的绝对路径。 
 * - `@ApiModelProperty` 注解说明了这个字段在Swagger文档中的描述。 
 * - `@NotBlank` 注解确保这个字段不为空或空白字符。 
 * 5. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 注解自动生成所有属性的getter和setter方法。 
 * 6. **文档注解**： 
 * - `@ApiModel` 注解提供了关于类的描述信息，方便生成API文档。 
 * 总结： 
 * - `RMImportReq` 类主要用于处理导入请求，其中包含一个必需的字符串字段 `path`，用来存放资源的绝对路径。 
 * - 通过使用 Swagger 注解，使得该类在API文档中有一个清晰的描述。 
 * - Lombok 注解简化了类的实现，减少了样板代码。 
**/
package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * rmimport请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("资管数据导入基础请求类")
public class RMImportReq implements Serializable {

    /**
     * 路径
     *
     * @see String
     */
    @ApiModelProperty("资源地址绝对路径")
    @NotBlank
    private String path;

}
