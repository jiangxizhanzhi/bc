/**
 * 这段代码定义了一个名为 `TrainPlanBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示训练计划业务对象（BO），并且使用了 Lombok 注解来简化 getter 和 setter 方法的编写。 
 * 具体分析如下： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.oj.course.service.bo;` 
 * - 这个类位于 `com.cyberpeace.oj.course.service.bo` 包中。 
 * 2. **导入语句**： 
 * - `import io.swagger.annotations.ApiModelProperty;` 
 * - `import lombok.Getter;` 
 * - `import lombok.Setter;` 
 * - `import java.io.Serializable;` 
 * - 导入了 Swagger 注解、Lombok 注解以及 `Serializable` 接口。 
 * 3. **类声明**： 
 * - `@Getter` 
 * - `@Setter` 
 * - `public class TrainPlanBo implements Serializable { ... }` 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 类实现 `Serializable` 接口，表明它可以被序列化。 
 * 4. **属性**： 
 * - `@ApiModelProperty("训练计划编号") private String resourceId;` 
 * - `resourceId`：表示资源 ID。 
 * - `@ApiModelProperty("训练计划名称") private String trainPlanName;` 
 * - `trainPlanName`：表示训练计划的名称。 
 * - `@ApiModelProperty("姓名") private String username;` 
 * - `username`：表示用户名。 
 * 5. **注释**： 
 * - 类和属性都有详细的 Javadoc 注释，说明了每个属性的作用和类型。 
 * 总结： 
 * - `TrainPlanBo` 类是一个简单的数据传输对象（DTO），用于在不同层之间传递训练计划的信息。 
 * - 使用了 Swagger 注解来描述 API 中的模型属性。 
 * - 使用 Lombok 注解简化了 getter 和 setter 方法的生成。 
 * - 实现了 `Serializable` 接口，可以进行对象的序列化操作。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 训练计划博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TrainPlanBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划编号")
	private String resourceId;

	/**
	 * 训练计划名称
	 *
	 * @see String
	 */
	@ApiModelProperty("训练计划名称")
	private String trainPlanName;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	@ApiModelProperty("姓名")
	private String username;

}
