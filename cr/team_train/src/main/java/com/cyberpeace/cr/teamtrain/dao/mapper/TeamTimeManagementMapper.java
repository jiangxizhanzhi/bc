package com.cyberpeace.cr.teamtrain.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTimeManagement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeamTimeManagementMapper extends BaseMapper<TeamTimeManagement> {

    TeamTimeManagement selectByRoomId(String roomId);

    List<String> selectRoomIdsByStatus(Integer status);

    List<TeamTimeManagement> selectAll();

    List<TeamTimeManagement> selectByStatus(@Param("status") Integer status);

    List<TeamTimeManagement> selectExcludeStatus(@Param("status") Integer status);
}
