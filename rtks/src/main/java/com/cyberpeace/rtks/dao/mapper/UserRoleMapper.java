package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.bo.UserRoleContext;

/**
 * 用户角色Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

	/**
	 * 查询都
	 *
	 * @return {@code List<UserRoleContext> }
	 */
	List<UserRoleContext> selectAll();
}
