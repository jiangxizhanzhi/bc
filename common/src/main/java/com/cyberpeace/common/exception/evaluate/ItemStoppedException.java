package com.cyberpeace.common.exception.evaluate;

/**
 * 项停止异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ItemStoppedException` 的自定义异常类，继承自 Java 的 `RuntimeException` 类。以下是简要分析： 
 * 1. **类的功能**： 
 * `ItemStoppedException` 是一个运行时异常（unchecked exception），用于表示某个“项”被停止的异常情况。 
 * 2. **继承关系**： 
 * 它继承自 `RuntimeException`，表明它是一个不需要显式声明的异常类型，通常用于程序逻辑错误或非预期的情况。 
 * 3. **元信息**： 
 * - 注释说明了该异常的作用是处理“项停止”的场景。 
 * - 作者为 `SN`，版本为 `1.0.0`，创建日期为 `2024/11/08`。 
 * - 它与 `RuntimeException` 存在继承关系，通过 `@see RuntimeException` 标记。 
 * 4. **用途**： 
 * 这个类可以用于在程序中抛出特定的运行时异常，例如当某个任务、流程或操作被人为停止时，可以通过抛出此异常来通知调用者。 
 * 总结：这是一个简单的自定义运行时异常类，主要用于标记“项停止”相关的错误场景。 
**/
public class ItemStoppedException extends RuntimeException{
}

