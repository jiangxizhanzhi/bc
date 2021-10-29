package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.Role
 */
@Mapper
@Repository
public interface CrRoleMapper extends BaseMapper<CrRole> {

	List<CrRole> selectCrRoleByRoomId(RoleReq roleReq);
}
