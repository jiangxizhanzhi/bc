package com.cyberpeace.common.constant;

/**
 * 调用类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/13
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CallType` 的公共类，位于包 `com.cyberpeace.common.constant` 中。该类主要用于定义一组常量字符串，表示不同的调用类型。以下是代码的简要分析： 
 * 1. **类功能**： 
 * - 类的主要目的是提供一组静态常量，用于描述不同类型的调用。这些常量是不可变的（`final`），并且通过类名直接访问。 
 * 2. **常量定义**： 
 * - `THEORY`：表示“理论知识库”，值为 `"theory"`。 
 * - `STRATEGY`：表示“策略库”，值为 `"strategy"`。 
 * - `TACTIC`：表示“战法库”，值为 `"tactic"`。 
 * - `ALGORITHM`：表示“算法”，值为 `"algorithm"`。 
 * - `RULE`：表示“规则”，值为 `"rule"`。 
 * 3. **注释说明**： 
 * - 每个常量都有详细的注释，解释其含义以及可能的用途（如与 `String` 关联）。 
 * - 类本身也有注释，明确指出其作用是定义调用类型。 
 * 4. **使用场景**： 
 * - 这些常量可以被其他类用来表示调用的具体类型，例如在配置文件、接口调用或逻辑分支中。 
 * 5. **设计特点**： 
 * - 使用了 `public static final` 的组合，确保常量全局可见且不可更改。 
 * - 常量命名采用全大写形式，符合 Java 常量命名规范。 
 * 总结：这是一个简单的工具类，提供了调用类型的常量定义，便于代码复用和维护。 
**/
public class CallType {

	/**
	 * 理论知识库
	 *
	 * @see String
	 */
	public static final String THEORY = "theory";

	/**
	 * 策略库
	 *
	 * @see String
	 */
	public static final String STRATEGY = "strategy";

	/**
	 * 战法库
	 *
	 * @see String
	 */
	public static final String TACTIC = "tactic";


	/**
	 * 算法
	 *
	 * @see String
	 */
	public static final String ALGORITHM = "algorithm";

	/**
	 * 规则
	 *
	 * @see String
	 */
	public static final String RULE = "rule";
}

