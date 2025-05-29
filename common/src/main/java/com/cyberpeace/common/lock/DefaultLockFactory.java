package com.cyberpeace.common.lock;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认锁工厂
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see LockFactory
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码实现了一个基于 `DefaultLockFactory` 的默认锁工厂类，用于生成不同类型的分布式锁或本地锁。它支持根据业务类型 (`businessType`) 和键值 (`key`) 获取对应的锁实例。 
 * --- 
 * #### 主要功能模块 
 * 1. **单例模式**： 
 * - 使用双重检查锁定（Double-Checked Locking）实现线程安全的单例模式。 
 * - 提供静态方法 `getInstance()` 返回唯一的 `LockFactory` 实例。 
 * 2. **锁管理**： 
 * - 使用 `ConcurrentHashMap` 存储业务锁 (`businessLockMap`)，以便按业务类型快速获取锁。 
 * - 支持两种锁类型： 
 * - 默认锁：使用 `ReentrantLock`。 
 * - 自定义锁：通过 `SegmentLock` 类实现。 
 * 3. **锁获取逻辑**： 
 * - 方法 `getLock(String businessType, String key)` 根据业务类型和键值生成锁实例。 
 * - 如果未指定锁类型 (`lockType`)，则根据业务类型和键值生成对应的锁： 
 * - 如果 `businessType` 和 `key` 都非空，则返回 `SegmentLock` 对应的子锁。 
 * - 如果只有 `businessType` 非空，则返回 `ReentrantLock`。 
 * - 如果指定了锁类型，则尚未实现具体逻辑，返回 `null`。 
 * 4. **锁类型配置**： 
 * - 通过系统属性 `System.getProperty("lockType")` 配置锁类型，默认为空。 
 * --- 
 * #### 优点 
 * 1. 线程安全：通过双重检查锁定确保单例模式的线程安全性。 
 * 2. 高效锁管理：利用 `ConcurrentHashMap` 快速存储和查找锁实例。 
 * 3. 可扩展性：支持自定义锁类型 (`SegmentLock`)，便于扩展其他锁机制。 
 * --- 
 * #### 缺点与改进建议 
 * 1. **未实现自定义锁类型逻辑**： 
 * - 当前代码中，如果设置了锁类型 (`lockType`)，直接返回 `null`。需要补充具体的锁类型处理逻辑。 
 * 2. **硬编码依赖**： 
 * - `SegmentLock` 类的具体实现未提供，可能需要进一步完善以支持实际场景。 
 * 3. **异常处理不足**： 
 * - 代码中未对空指针、非法参数等异常情况进行处理，可能导致运行时错误。 
 * --- 
 * #### 总结 
 * 该代码实现了一个基本的锁工厂，支持单例模式和简单的锁管理逻辑。但部分功能（如自定义锁类型）尚未实现，需要进一步完善以满足实际需求。 
**/
public class DefaultLockFactory implements LockFactory {
    /**
     * 实例
     *
     * @see LockFactory
     */
    private static volatile LockFactory instance;
    /**
     * 业务锁地图
     *
     * @see Map<String, Object>
     */
    private final Map<String, Object> businessLockMap;
    /**
     * 锁类型
     *
     * @see String
     */
    private String lockType = System.getProperty("lockType");
    /**
     * 锁
     *
     * @see Lock
     */
    private static Lock lock = new ReentrantLock();

    private DefaultLockFactory() {
        this.businessLockMap = new ConcurrentHashMap<>();
    }

    public static LockFactory getInstance() {
        if (instance == null) {
            synchronized (LockFactory.class) {
                if (instance == null) {
                    instance = new DefaultLockFactory();
                }
            }
        }
        return instance;
    }
    @Override
    public Lock getLock(String businessType,String key) {
        if (StringUtils.isBlank(lockType)) {
            if (StringUtils.isNotBlank(businessType)) {
                if (StringUtils.isNotBlank(key)) {
                    SegmentLock segmentLock = (SegmentLock)businessLockMap.computeIfAbsent(businessType,k->new SegmentLock());
                    return segmentLock.getLock(key);
                } else {
                    return (ReentrantLock)businessLockMap.computeIfAbsent(businessType,k->new ReentrantLock());
                }
            }

            return lock;
        }

        return null;
    }
}

