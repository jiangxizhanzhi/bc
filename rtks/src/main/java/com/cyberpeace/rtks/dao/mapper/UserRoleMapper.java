package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.bo.UserRoleContext;

@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

	List<UserRoleContext> selectAll();
}
