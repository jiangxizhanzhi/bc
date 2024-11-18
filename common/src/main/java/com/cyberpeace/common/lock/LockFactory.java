package com.cyberpeace.common.lock;

import java.util.concurrent.locks.Lock;

/**
 * 锁具厂
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface LockFactory {
     /**
      * 获取锁
      *
      * @param businessType 业务类型
      * @param key          钥匙
      * @return {@code Lock }
      */
     Lock getLock(String businessType,String key);
}
