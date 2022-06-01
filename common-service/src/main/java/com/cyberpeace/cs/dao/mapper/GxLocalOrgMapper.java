package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import com.cyberpeace.cs.dao.entity.GxLocalOrg;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface GxLocalOrgMapper extends BaseMapper<GxLocalOrg> {

	List<String> getByClassRoomId(String classRoomId);

	List<String> getOrgIdsByUserId(String userId);

}
