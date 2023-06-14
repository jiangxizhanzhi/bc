package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;

import java.util.Collection;
import java.util.List;

public interface ScreenService {


    /**
     *
     * @param roomId 房间Id
     * @param replaySceneId 复盘用的场景id，每次关闭场景时更新
     * @param nodeId 设备Id
     * @return
     */
    List<ScreenRecordUserRole> getReplayUserTimeInfo(String roomId, String replaySceneId , String nodeId, Collection<Integer> groups);
}
