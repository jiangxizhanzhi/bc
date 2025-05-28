/**
 * 这段代码定义了一个名为 `MpImageInfo` 的 Java 类，位于包 `com.cyberpeace.integratation.model.mp` 下。以下是对该代码的简要分析： 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.integratation.model.mp; 
 * ``` 
 * 这行代码声明了该类所在的包路径为 `com.cyberpeace.integratation.model.mp`。 
 * 2. **导入语句**： 
 * ```java 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * 这两行代码导入了 Lombok 库中的 `@Getter` 和 `@Setter` 注解。Lombok 是一个简化 Java 代码的库，通过注解自动生成常见的样板代码（如 getter 和 setter 方法）。 
 * 3. **类声明**： 
 * ```java 
 * @Getter 
 * @Setter 
 * public class MpImageInfo { 
 * } 
 * ``` 
 * - `public class MpImageInfo`：声明了一个公共类 `MpImageInfo`。 
 * - `@Getter` 和 `@Setter`：这两个 Lombok 注解会自动生成该类中所有非静态字段的 getter 和 setter 方法。 
 * 4. **类文档**： 
 * ```java 
 * /** 
 * * 中台图像信息 
 * * 
 * * @author SN 
 * * @date 2024/11/08 
 * */ 
 * ``` 
 * 这是类的文档注释，描述了这个类的作用，并指明了作者和创建日期。 
 * 总结： 
 * - `MpImageInfo` 类用于表示某种图像信息。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解，避免了手动编写 getter 和 setter 方法。 
 * - 目前这个类没有包含任何字段或方法，只是一个空类。 
**/
package com.cyberpeace.integratation.model.mp;

import lombok.Getter;
import lombok.Setter;

/**
 * 中台图像信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MpImageInfo {

}
