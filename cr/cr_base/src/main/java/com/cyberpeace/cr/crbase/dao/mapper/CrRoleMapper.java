package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.Role
 */
@Mapper
@Repository
public interface CrRoleMapper extends BaseMapper<CrRole> {

    List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery);

    List<String> getRoomIdsByUserId(String userId);

    CrRole getCrRoleByRoomIdAndUserId(String trainingTeamId, String userId);
}
