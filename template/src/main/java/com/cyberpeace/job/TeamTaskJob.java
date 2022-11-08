package com.cyberpeace.job;

import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * 推送已过期任务事件, 动态更新页面
 */
@Slf4j
@Component
public class TeamTaskJob {

    @Autowired
    private TeamRoomService teamRoomService;

    @Autowired
    private CrTaskService crTaskService;

    private final int timeRate = 1000 * 5;

    @PostConstruct
    private void init() {
        teamRoomService.initOngoingTasks();
    }

    @Scheduled(fixedRate = 1000 * 5)
    private void checkTask() {
        List<CrTask> crTaskList = teamRoomService.getOngoingTasks();
        if (crTaskList.isEmpty()) {
            return;
        }

        Date now = new Date();
        long remainingTime30s = now.getTime() + 1000 * 30;

        crTaskList.removeIf(crTask -> {
            // 结束时间
            long closeTime = crTask.getCloseTime().getTime();

            // 任务即将结束
            if (closeTime + timeRate > remainingTime30s && remainingTime30s > closeTime) {
                log.info(String.format("Task %s about to expire: %s", crTask.getResourceId(), crTask.getCloseTime()));
            }

            // 任务结束
            if (now.getTime() > closeTime) {
                log.info(String.format("Task %s expired: %s", crTask.getResourceId(), crTask.getCloseTime()));
                crTaskService.pushMissionWSByTaskUser(crTask.getRoomId(), crTask.getUserId(), MissionWsCode.TEAM_MISSION_CODE);
                return true;
            }
            return false;
        });
    }
}
