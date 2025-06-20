package com.cyberpeace.gateway.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 外部执业类 bo
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ExternalPracticeCategoryBo` 的 Java 类，该类实现了 `Serializable` 接口，并使用了 Lombok 注解来简化代码。以下是对这段代码的详细分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.gateway.service.bo; 
 * ``` 
 * - 这行代码声明了该类所在的包为 `com.cyberpeace.gateway.service.bo`。 
 * ### 导入的依赖 
 * ```java 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * ``` 
 * - `ApiModel` 和 `ApiModelProperty` 是 Swagger 注解，用于生成 API 文档。 
 * - `@Getter` 和 `@Setter` 是 Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `Serializable` 是 Java 标准库中的接口，表示该类可以序列化。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * @ApiModel 
 * public class ExternalPracticeCategoryBo implements Serializable { 
 * ``` 
 * - `@Getter` 和 `@Setter` 注解自动为类中的所有属性生成 getter 和 setter 方法。 
 * - `@ApiModel` 注解用于描述这个类在 Swagger 文档中的信息。 
 * - `ExternalPracticeCategoryBo` 类实现了 `Serializable` 接口，因此可以进行序列化。 
 * ### 属性定义 
 * ```java 
 * /** 
 * * 资源id 
 * *
 * @ApiModelProperty("分类编号") 
 * private String resourceId; 
 * /** 
 * * 名称 
 * *
 * @ApiModelProperty("分类名称") 
 * private String name; 
 * ``` 
 * - `resourceId` 和 `name` 是类的两个私有属性。 
 * - `@ApiModelProperty` 注解用于描述这些属性在 Swagger 文档中的信息。 
 * ### 总结 
 * - `ExternalPracticeCategoryBo` 类是一个简单的数据传输对象（DTO），用于表示外部执业类的相关信息。 
 * - 使用 Lombok 注解简化了代码，避免了手动编写 getter 和 setter 方法。 
 * - 通过 Swagger 注解，使得该类可以在生成的 API 文档中被清晰地描述出来。 
 * - 该类实现了 `Serializable` 接口，说明它可能需要在网络传输或持久化存储中使用。 
 * 整体来说，这是一个简洁且功能明确的类，适用于需要在不同层之间传递数据的场景。 
**/
@Getter
@Setter
@ApiModel
public class ExternalPracticeCategoryBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
     /**
      * 资源id
      */
     @ApiModelProperty("分类编号")
     private String resourceId;

     /**
      * 名称
      */
     @ApiModelProperty("分类名称")
     private String name;

}

