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
