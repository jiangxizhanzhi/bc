package com.cyberpeace.common.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 段锁
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 该代码实现了一个基于分段锁的设计模式，目的是通过将锁分散到多个小锁（分段锁）中，减少锁竞争，提高并发性能。以下是代码的简要分析： 
 * 1. **功能概述**： 
 * - 该类 `SegmentLock` 提供了一种分段锁机制，用于管理多个锁实例。 
 * - 通过计算哈希值对分段数量取模，确定某个键对应的分段索引，进而获取对应的锁。 
 * 2. **关键特性**： 
 * - **分段锁设计**：使用数组存储多个 `ReentrantLock` 实例，每个锁负责一个分段的资源保护。 
 * - **默认分段数量**：默认分段数量为 16 (`defaultSegmentCount`)，可以根据需要调整。 
 * - **锁分配策略**：通过 `key` 的哈希值对分段数量取模，确保键与锁之间的映射关系均匀分布。 
 * 3. **主要方法**： 
 * - **构造函数**：初始化分段锁数组，并为每个分段创建一个 `ReentrantLock` 实例。 
 * - **`getSegment` 方法**：根据输入的 `key` 计算其所属的分段索引。 
 * - **`getLock` 方法**：根据 `key` 获取对应的分段锁。 
 * 4. **适用场景**： 
 * - 适用于需要高并发访问但锁冲突较多的场景，例如共享资源的多线程访问。 
 * - 可以通过调整分段数量来平衡锁粒度和并发性能。 
 * 5. **潜在改进点**： 
 * - 默认分段数量固定为 16，可能需要根据实际应用场景动态调整。 
 * - 缺乏异常处理机制，例如在构造函数或锁操作中未处理可能的异常。 
 * 总体而言，该代码提供了一个简单而有效的分段锁实现，适合需要细粒度控制并发访问的场景。 
**/
public class SegmentLock {

    /**
     * 违约段计数
     */
    private int defaultSegmentCount = 16;
    /**
     * 锁
     *
     * @see Lock[]
     */
    private final Lock[] locks;

    /**
     * 段锁
     */
    public SegmentLock() {
        this.locks = new ReentrantLock[defaultSegmentCount];
        for (int i = 0; i < defaultSegmentCount; i++) {
            locks[i] = new ReentrantLock();
        }
    }

    /**
     * 获取段
     *
     * @param key 钥匙
     * @return int
     */
    private int getSegment(String key) {
        return Math.abs(key.hashCode() % defaultSegmentCount);
    }

    /**
     * 获取锁
     *
     * @param key 钥匙
     * @return {@code Lock }
     */
    public Lock getLock(String key) {
        int segment = getSegment(key);
        return locks[segment];
    }

}

