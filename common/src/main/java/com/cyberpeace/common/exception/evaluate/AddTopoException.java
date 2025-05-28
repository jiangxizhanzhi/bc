/**
 * 这段代码定义了一个名为 `AddTopoException` 的自定义异常类，继承自 Java 的标准运行时异常类 `RuntimeException`。以下是简要分析： 
 * 1. **类的功能**： 
 * - `AddTopoException` 是一个新增拓扑相关的异常类，用于表示在操作拓扑（topology）时发生的错误。 
 * 2. **继承关系**： 
 * - 它继承自 `RuntimeException`，表明这是一个运行时异常，通常不需要强制捕获（除非开发者选择捕获）。 
 * 3. **元信息**： 
 * - 注释中说明了作者为 `SN`，版本号为 `1.0.0`，创建日期为 `2024/11/08`。 
 * - 指定了该类与 `RuntimeException` 的继承关系，并标注了其适用范围（`@since 1.0.0`）。 
 * 4. **用途**： 
 * - 该类可以用于在拓扑相关业务逻辑中抛出异常，以通知调用方发生了错误。 
 * 总结：这是一个简单的自定义运行时异常类，用于处理新增拓扑相关的错误场景。 
**/
package com.cyberpeace.common.exception.evaluate;

/**
 * 新增拓扑例外
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
public class AddTopoException extends RuntimeException{
}
