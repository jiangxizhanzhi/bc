/**
 * 这段代码定义了一个名为 `YxRvbUserColl` 的类，该类位于包 `com.cyberpeace.integratation.service.bo` 中。这个类使用了 Lombok 库来自动生成 getter 和 setter 方法。下面是对代码的详细分析： 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.integratation.service.bo; 
 * ``` 
 * 这行代码指定了该类所在的包。 
 * 2. **导入语句**： 
 * ```java 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import org.apache.commons.compress.utils.Lists; 
 * import java.util.List; 
 * ``` 
 * - `lombok.Getter` 和 `lombok.Setter`：这两个注解由 Lombok 库提供，用于自动生成属性的 getter 和 setter 方法。 
 * - `org.apache.commons.compress.utils.Lists`：这是一个来自 Apache Commons Compress 库的工具类，提供了静态方法来创建不可变列表。 
 * - `java.util.List`：这是 Java 标准库中的接口，表示一个动态数组。 
 * 3. **类声明**： 
 * ```java 
 * @Getter 
 * @Setter 
 * public class YxRvbUserColl { 
 * ``` 
 * - `@Getter` 和 `@Setter` 注解：这些注解由 Lombok 提供，用于自动生成 `commander`、`middle` 和 `worker` 属性的 getter 和 setter 方法。 
 * - `public class YxRvbUserColl`：定义了一个公共类 `YxRvbUserColl`。 
 * 4. **属性声明**： 
 * ```java 
 * private List<YxUserInfo> commander = Lists.newArrayList(); 
 * private List<YxUserInfo> middle = Lists.newArrayList(); 
 * private List<YxUserInfo> worker = Lists.newArrayList(); 
 * ``` 
 * - `private List<YxUserInfo> commander`：这是一个私有属性，类型为 `List<YxUserInfo>`，初始值为 `Lists.newArrayList()` 创建的空列表。 
 * - `private List<YxUserInfo> middle`：同上，但名称不同。 
 * - `private List<YxUserInfo> worker`：同上，但名称不同。 
 * 5. **总结**： 
 * - `YxRvbUserColl` 类用于存储不同类型用户的信息（如指挥官、中层和工人），每个类型的用户信息都以列表的形式存储。 
 * - 使用 Lombok 库简化了 getter 和 setter 方法的编写。 
 * - 列表的初始值是通过 `Lists.newArrayList()` 创建的空列表，这有助于确保对象在创建时有一个明确的状态。 
 * 这段代码主要用于在某个集成服务中管理不同类型用户的集合。 
**/
package com.cyberpeace.integratation.service.bo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Getter
@Setter
public class YxRvbUserColl {

	private List<YxUserInfo> commander = Lists.newArrayList();

	private List<YxUserInfo> middle = Lists.newArrayList();

	private List<YxUserInfo> worker = Lists.newArrayList();

}
