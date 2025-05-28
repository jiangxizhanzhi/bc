/**
 * 这段代码定义了一个接口 `LockFactory`，用于创建锁（`Lock`）。以下是简要分析： 
 * 1. **功能描述**： 
 * - 接口 `LockFactory` 提供了一个方法 `getLock`，用于根据业务类型 (`businessType`) 和键值 (`key`) 获取一个锁对象。 
 * 2. **技术细节**： 
 * - 方法签名：`Lock getLock(String businessType, String key)`。 
 * - 参数： 
 * - `businessType`：标识锁所属的业务类型。 
 * - `key`：唯一标识锁的键值。 
 * - 返回值：实现了 `java.util.concurrent.locks.Lock` 接口的对象。 
 * 3. **设计意图**： 
 * - 该接口适用于需要根据不同业务场景动态生成锁的场景，例如分布式锁或并发控制。 
 * - 实现类可以根据 `businessType` 和 `key` 的组合逻辑来决定返回具体的锁实现。 
 * 4. **适用场景**： 
 * - 可用于多线程环境下的资源保护或业务逻辑隔离。 
 * - 支持灵活扩展，不同业务场景可以有各自的锁实现。 
 * 总结：这是一个通用的锁工厂接口，通过传入业务类型和键值，能够动态生成所需的锁对象。 
**/
package com.cyberpeace.common.lock;

import java.util.concurrent.locks.Lock;

/**
 * 锁具厂
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface LockFactory {
     Lock getLock(String businessType,String key);
}
