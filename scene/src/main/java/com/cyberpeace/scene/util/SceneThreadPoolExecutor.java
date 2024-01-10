package com.cyberpeace.scene.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.cyberpeace.common.component.MyWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author :   SN
 * @date :   2023/12/27 10:27
 * @desc :
 **/
@Slf4j
public class SceneThreadPoolExecutor extends ThreadPoolExecutor {

    MyWebSocketHandler webSocketHandler;

    public void setWebSocketHandler(MyWebSocketHandler webSocketHandler){
        this.webSocketHandler = webSocketHandler;
    }

    public SceneThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}
