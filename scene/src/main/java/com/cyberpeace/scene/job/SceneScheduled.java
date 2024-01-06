package com.cyberpeace.scene.job;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.scene.service.SceneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author :   SN
 * @date :   2024/1/5 9:33
 * @desc :
 **/
@Slf4j
@Component
@EnableScheduling
public class SceneScheduled {

    private String REPAIR_FILE;

    @Autowired
    private SceneService sceneService;

    @PostConstruct
    public void init(){
        REPAIR_FILE = ConfigUtil.getContextProperty("scene.repair.file");
    }

    /**
     * 一键修复，30秒执行一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void sceneRepair(){
        sceneService.repair(REPAIR_FILE);
    }

    /**
     * 自动销毁，30秒执行一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void autoDestroy(){
//        sceneService.autoDestroy();
    }

    /**
     * 场景状态检查，30秒执行一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void sceneState(){
        sceneService.sceneState();
    }
}
