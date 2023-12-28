package com.cyberpeace.scene.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author :   SN
 * @date :   2023/12/27 10:27
 * @desc :
 **/
public class SceneThreadPoolExecutor extends ThreadPoolExecutor {

    public SceneThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}
