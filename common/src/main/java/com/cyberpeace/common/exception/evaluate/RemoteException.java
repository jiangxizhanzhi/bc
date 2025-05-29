package com.cyberpeace.common.exception.evaluate;

/**
 * 远程异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `RemoteException` 的自定义异常类，继承自 Java 标准库中的 `RuntimeException` 类。以下是对其的简要分析： 
 * ### 主要特点： 
 * 1. **类名与功能**： 
 * - 类名为 `RemoteException`，根据注释说明，该类用于表示远程调用相关的异常。 
 * 2. **继承关系**： 
 * - 继承自 `RuntimeException`，这意味着它是一个运行时异常（unchecked exception），不需要强制要求调用者在代码中显式处理。 
 * 3. **元信息**： 
 * - 注解了作者 (`@author SN`)、版本 (`@version 1.0.0`) 和创建日期 (`@date 2024/11/08`)。 
 * - 指定了继承的父类 `RuntimeException` (`@see RuntimeException`)。 
 * 4. **用途**： 
 * - 通常用于远程调用场景（如网络通信、微服务间通信等）出现错误时抛出。 
 * ### 总结： 
 * 这是一个简单的自定义运行时异常类，用于封装远程调用失败的场景，简化了异常处理逻辑，同时保持代码的可读性与扩展性。 
**/
public class RemoteException extends RuntimeException{
}

