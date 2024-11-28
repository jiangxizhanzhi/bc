package com.cyberpeace.common.lock;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultLockFactory implements LockFactory {
    private final Map<String, Object> businessLockMap;
    private String lockType = System.getProperty("lockType");
    private static Lock lock = new ReentrantLock();

    private DefaultLockFactory() {
        this.businessLockMap = new ConcurrentHashMap<>();
    }

    public static LockFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final LockFactory INSTANCE = new DefaultLockFactory();
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
