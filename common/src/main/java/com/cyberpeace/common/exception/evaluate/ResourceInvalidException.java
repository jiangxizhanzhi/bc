package com.cyberpeace.common.exception.evaluate;

/**
 * 资源无效异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个自定义异常类 `ResourceInvalidException`，继承自 Java 标准库中的 `RuntimeException`。以下是对其的简要分析： 
 * 1. **功能定位**： 
 * - 该类用于表示资源无效时抛出的异常，属于运行时异常（Unchecked Exception），因此调用者无需显式捕获。 
 * 2. **继承关系**： 
 * - 它继承自 `RuntimeException`，表明这是一种运行时异常，通常用于程序逻辑错误或不可恢复的情况。 
 * 3. **构造方法**： 
 * - 提供了两个构造方法： 
 * - 无参构造方法：直接调用父类的无参构造方法。 
 * - 带参构造方法：接收一个 `String` 类型的消息参数，并将其传递给父类的带参构造方法，以便设置异常信息。 
 * 4. **注释说明**： 
 * - 类上有详细的 Javadoc 注释，描述了异常的作用、作者、版本、日期等信息，便于其他开发者理解其用途。 
 * 5. **适用场景**： 
 * - 当某个资源（如文件、数据库连接等）无效时，可以抛出此异常，提示调用方进行处理。 
 * 总结：这是一个标准的自定义运行时异常类，设计简洁且符合 Java 异常机制的最佳实践。 
**/
public class ResourceInvalidException extends RuntimeException {

	public ResourceInvalidException(String message) {
		super(message);
	}

	public ResourceInvalidException() {
		super();
	}
}

