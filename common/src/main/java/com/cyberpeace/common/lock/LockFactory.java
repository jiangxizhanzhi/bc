package com.cyberpeace.common.lock;

import java.util.concurrent.locks.Lock;

/**
 * 锁具厂
 *
 * @author SN
 * @date 2024/11/08
 */
public interface LockFactory {
     Lock getLock(String businessType,String key);
}
