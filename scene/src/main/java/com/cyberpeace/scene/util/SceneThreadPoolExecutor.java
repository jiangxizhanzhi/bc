package com.cyberpeace.scene.util;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.scene.constant.enums.StageEnum;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.job.SceneExecutor;
import com.cyberpeace.scene.service.NetworkService;
import com.cyberpeace.scene.service.NodeService;
import com.cyberpeace.scene.service.SceneService;
import com.cyberpeace.scene.service.bo.SceneWsIdentifier;
import com.cyberpeace.scene.service.bo.WsScene;
import com.cyberpeace.scene.service.bo.WsSceneData;
import com.cyberpeace.scene.service.bo.WsSceneStage;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
