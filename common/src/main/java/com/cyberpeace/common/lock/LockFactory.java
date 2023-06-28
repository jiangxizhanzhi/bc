package com.cyberpeace.common.lock;

import java.util.concurrent.locks.Lock;

public interface LockFactory {
     Lock getLock(String businessType,String key);
}
