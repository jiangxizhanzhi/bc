package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;

@Mapper
@Repository
public interface TeamCommonDeviceMapper extends BaseMapper<TeamCommonDevice> {

    List<TeamCommonDevice> selectByCollectUserAndRoomId(@Param("roomId") String roomId, @Param("collectUserId") String collectUserId);

    void deleteByResourceId(@Param("resourceId") String resourceId);

	void deleteByDeviceId(@Param("nodeId") String nodeId);

    void insertList(@Param("teamCommonDeviceList") List<TeamCommonDevice> teamCommonDeviceList);
}
