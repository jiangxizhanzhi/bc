package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.GxOrgUsers;

@Mapper
public interface GxOrgUsersMapper extends BaseMapper<GxOrgUsers> {

	List<String> getByClassRoomId(String classRoomId);

	List<String> getOrgIdsByUserId(String userId);

}
