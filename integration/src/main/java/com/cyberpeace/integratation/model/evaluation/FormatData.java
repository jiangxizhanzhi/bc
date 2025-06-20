package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 设置数据格式
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `FormatData` 的 Java 类，位于包 `com.cyberpeace.integratation.model.evaluation` 中。以下是对其主要特性的简要分析： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.integratation.model.evaluation;` 声明了该类所在的包路径。 
 * 2. **导入语句**： 
 * - `import java.io.Serializable;` 导入了 `Serializable` 接口，这意味着 `FormatData` 类可以被序列化。 
 * - `import java.util.List;` 导入了 `List` 接口，表示 `data` 属性将是一个列表。 
 * - `import lombok.Getter;` 和 `import lombok.Setter;` 导入了 Lombok 注解，用于简化 getter 和 setter 方法的生成。 
 * 3. **类声明**： 
 * - `@Getter` 和 `@Setter` 是 Lombok 提供的注解，用于自动生成属性的 getter 和 setter 方法。 
 * - `public class FormatData implements Serializable` 声明了一个公共类 `FormatData`，该类实现了 `Serializable` 接口。 
 * 4. **属性**： 
 * - `private List<String> data;` 定义了一个私有属性 `data`，类型为 `List<String>`，用来存储字符串列表数据。 
 * 5. **文档注释**： 
 * - 类上有 Javadoc 注释，描述了这个类的功能，并注明了作者和日期。 
 * 总结： 
 * - 这个类的主要目的是封装一个字符串列表的数据，并且允许该数据被序列化。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解来减少样板代码，使类更加简洁。 
 * - 这个类适用于需要处理和传输字符串列表数据的场景。 
**/
@Getter
@Setter
public class FormatData implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 数据
	 */
	List<String> data;
}

