package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.entity.RbCommonDevice;

/**
 * 红蓝对抗常用装置Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
public interface RbCommonDeviceMapper extends BaseMapper<RbCommonDevice> {
    /**
     * 查询由收集用户和房间id
     *
     * @param roomId        房间id
     * @param collectUserId 收集用户id
     * @return {@code List<RbCommonDevice> }
     */
    List<RbCommonDevice> selectByCollectUserAndRoomId(@Param("roomId") String roomId, @Param("collectUserId") String collectUserId);

    /**
     * 查询全部设备
     *
     * @param roomId 房间id
     * @return {@code List<RbCommonDevice> }
     */
    List<RbCommonDevice> selectAllDevices(@Param("roomId") String roomId);

    /**
     * 插入列表
     *
     * @param commonDeviceList 常用装置列表
     */
    void insertList(@Param("commonDeviceList") List<RbCommonDevice> commonDeviceList);

    /**
     * 删除由资源id
     *
     * @param resourceId 资源id
     */
    void deleteByResourceId(@Param("resourceId") String resourceId);

    /**
     * 删除由装置id
     *
     * @param deviceId 装置id
     */
    void deleteByDeviceId(@Param("deviceId") String deviceId);

    /**
     * 删除由房间id
     *
     * @param roomId 房间id
     */
    void deleteByRoomId(@Param("roomId") String roomId);
}
