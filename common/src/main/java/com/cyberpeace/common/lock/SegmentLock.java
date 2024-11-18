package com.cyberpeace.common.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 段锁
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public class SegmentLock {

    /**
     * 默认段计数
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
