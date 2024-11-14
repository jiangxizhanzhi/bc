package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainDevice;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainDeviceContext;

import java.util.List;

/**
 * 团队装置Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface TeamDeviceService extends IService<TeamTrainDevice> {
    /**
     * 获取占用主机
     *
     * @param roomId   团队房间id
     * @param deviceId 主机id
     * @return 占用主机
     */
    TeamTrainDevice getOccupied(String roomId, String deviceId);

    /**
     * 占用主机
     *
     * @param device 主机实体
     * @return 布尔值
     */
    boolean occupy(TeamTrainDevice device);

    /**
     * 解除占用
     *
     * @param roomId   团队房间id
     * @param deviceId 主机id
     * @param userId   用户id
     * @return 布尔值
     */
    boolean remove(String roomId, String deviceId, String userId);

    /**
     * 获取占用的主机
     *
     * @param roomId   团队房间id
     * @param deviceId 主机id
     * @return 占用设备列表
     */
    List<TeamTrainDeviceContext> getOccupies(String roomId, String deviceId);
}
