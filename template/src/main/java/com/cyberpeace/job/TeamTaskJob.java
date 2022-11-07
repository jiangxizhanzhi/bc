package com.cyberpeace.job;

import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
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

        log.info(String.format("Check that %s tasks in progress.", crTaskList.size()));
        Date now = new Date();
        crTaskList.removeIf(crTask -> {
            log.info(String.format("now: %s, close_time: %s", now, crTask.getCloseTime()));
            if (now.getTime() > crTask.getCloseTime().getTime()) {
                crTaskService.pushMissionWSByTaskUser(crTask.getRoomId(), crTask.getUserId(), MissionWsCode.TEAM_MISSION_CODE);
                return true;
            }
            return false;
        });
    }
}
