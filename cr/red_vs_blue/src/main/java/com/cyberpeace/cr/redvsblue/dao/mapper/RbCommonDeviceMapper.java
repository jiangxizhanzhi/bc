package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;

/**
 * RB常见装置Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
public interface RbCommonDeviceMapper extends BaseMapper<RbCommonDevice> {
    /**
     * 查询由收集用户和房间id
     *
     * @param roomId        房间id
     * @param collectUserId 收集用户id
     * @return {@code List<RbCommonDevice> }
     * @throws
     * @see String
     * @see String
     * @see List<RbCommonDevice>
     */
    List<RbCommonDevice> selectByCollectUserAndRoomId(@Param("roomId") String roomId, @Param("collectUserId") String collectUserId);

    /**
     * 查询都设备
     *
     * @param roomId 房间id
     * @return {@code List<RbCommonDevice> }
     * @throws
     * @see String
     * @see List<RbCommonDevice>
     */
    List<RbCommonDevice> selectAllDevices(@Param("roomId") String roomId);

    /**
     * 插入列表
     *
     * @param commonDeviceList 常用设备列表
     * @throws
     * @see List<RbCommonDevice>
     */
    void insertList(@Param("commonDeviceList") List<RbCommonDevice> commonDeviceList);

    /**
     * 删除由资源id
     *
     * @param resourceId 资源id
     * @throws
     * @see String
     */
    void deleteByResourceId(@Param("resourceId") String resourceId);

    /**
     * 删除由装置id
     *
     * @param deviceId 装置id
     * @throws
     * @see String
     */
    void deleteByDeviceId(@Param("deviceId") String deviceId);

    /**
     * 删除由房间id
     *
     * @param roomId 房间id
     * @throws
     * @see String
     */
    void deleteByRoomId(@Param("roomId") String roomId);
}
