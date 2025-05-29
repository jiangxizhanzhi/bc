package com.cyberpeace.integratation.service.bo.mp;

import lombok.Getter;
import lombok.Setter;

/**
 * 这段代码定义了一个名为 `VirtualUsbQueryParams` 的 Java 类，该类位于包 `com.cyberpeace.integratation.service.bo.mp` 中。这个类使用了 Lombok 库来简化 getter 和 setter 方法的生成。 
 * ### 详细分析： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.integratation.service.bo.mp;` 
 * - 这行代码指定了类所在的包路径。 
 * 2. **导入语句**： 
 * - `import lombok.Getter;` 
 * - `import lombok.Setter;` 
 * - 这两行代码导入了 Lombok 库中的 `Getter` 和 `Setter` 注解。 
 * 3. **类声明**： 
 * - `@Getter @Setter public class VirtualUsbQueryParams { ... }` 
 * - 使用 `@Getter` 和 `@Setter` 注解来自动生成类中所有字段的 getter 和 setter 方法。 
 * - `public` 关键字表示这个类是公共的，可以被其他包中的类访问。 
 * 4. **字段声明**： 
 * - `private String name;` 
 * - 这是一个私有字符串类型的字段，用于存储名称。 
 * - `private Integer page;` 
 * - 这是一个私有整数类型的字段，用于存储分页信息。 
 * - `private Integer per_page;` 
 * - 这是一个私有整数类型的字段，用于存储每页显示的项目数量。 
 * ### 总结： 
 * - `VirtualUsbQueryParams` 类用于封装查询虚拟USB设备时可能用到的参数，包括设备名称、分页信息等。 
 * - 通过使用 Lombok 的 `@Getter` 和 `@Setter` 注解，简化了类的实现，避免了手动编写 getter 和 setter 方法。 
 * - 这个类适用于需要查询虚拟USB设备的场景，并且可能涉及到分页展示结果的需求。 
**/
@Getter
@Setter
public class VirtualUsbQueryParams {

	private String name;

	private Integer page;

	private Integer per_page;
}

