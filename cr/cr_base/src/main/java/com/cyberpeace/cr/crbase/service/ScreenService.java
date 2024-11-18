package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 屏幕Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
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
     * @throws
     * @see String
     * @see String
     * @see String
     * @see Collection<Integer>
     * @see List<ScreenRecordUserRole>
     */
    List<ScreenRecordUserRole> getReplayUserTimeInfo(String roomId, String replaySceneId , String nodeId, Collection<Integer> groups);

    /**
     * 过滤结束时间之前
     *
     * @param screenRecordUserRoles 复盘时间
     * @param actualEndTime         场景结束时间
     * @return
     * @throws
     * @see List<ScreenRecordUserRole>
     * @see Date
     * @see List<ScreenRecordUserRole>
     */
    List<ScreenRecordUserRole> filterBeforeActualEndTime(List<ScreenRecordUserRole> screenRecordUserRoles, Date actualEndTime);
}
