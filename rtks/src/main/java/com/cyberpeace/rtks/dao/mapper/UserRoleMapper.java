package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.bo.UserRoleContext;

/**
 * 用户岗位Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

	/**
	 * 查询全部
	 *
	 * @return {@code List<UserRoleContext> }
	 * @throws
	 * @see List<UserRoleContext>
	 */
	List<UserRoleContext> selectAll();
}
