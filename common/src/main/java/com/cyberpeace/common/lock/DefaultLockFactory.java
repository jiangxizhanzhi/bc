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
 * @date 2024/11/07
 * @see LockFactory
 * @since 1.0.0
 */
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

    /**
     * 默认锁工厂
     */
    private DefaultLockFactory() {
        this.businessLockMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取实例
     *
     * @return {@code LockFactory }
     */
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

    /**
     * 获取锁
     *
     * @param businessType 业务类型
     * @param key          钥匙
     * @return {@code Lock }
     */
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
