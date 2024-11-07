package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 屏幕Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface ScreenService {


    /**
     * 获取复盘用户时间信息
     *
     * @param roomId        房间Id
     * @param replaySceneId 复盘用的场景id，每次关闭场景时更新
     * @param nodeId        设备Id
     * @param groups        组
     * @return
     */
    List<ScreenRecordUserRole> getReplayUserTimeInfo(String roomId, String replaySceneId , String nodeId, Collection<Integer> groups);

    /**
     * 过滤结束时间之前
     *
     * @param screenRecordUserRoles 复盘时间
     * @param actualEndTime         场景结束时间
     * @return
     */
    List<ScreenRecordUserRole> filterBeforeActualEndTime(List<ScreenRecordUserRole> screenRecordUserRoles, Date actualEndTime);
}
