package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;

/**
 * 团队常用装置Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface TeamCommonDeviceMapper extends BaseMapper<TeamCommonDevice> {

    /**
     * 查询由收集用户和房间id
     *
     * @param roomId        房间id
     * @param collectUserId 收集用户id
     * @return {@code List<TeamCommonDevice> }
     */
    List<TeamCommonDevice> selectByCollectUserAndRoomId(@Param("roomId") String roomId, @Param("collectUserId") String collectUserId);

    /**
     * 查询全部设备
     *
     * @param roomId 房间id
     * @return {@code List<TeamCommonDevice> }
     */
    List<TeamCommonDevice> selectAllDevices(@Param("roomId") String roomId);

    /**
     * 删除由资源id
     *
     * @param resourceId 资源id
     */
    void deleteByResourceId(@Param("resourceId") String resourceId);

    /**
     * 删除由装置id
     *
     * @param nodeId 节点id
     */
    void deleteByDeviceId(@Param("nodeId") String nodeId);

    /**
     * 删除由房间id
     *
     * @param roomId 房间id
     */
    void deleteByRoomId(@Param("roomId") String roomId);

    /**
     * 插入列表
     *
     * @param teamCommonDeviceList 团队常用装置列表
     */
    void insertList(@Param("teamCommonDeviceList") List<TeamCommonDevice> teamCommonDeviceList);
}
