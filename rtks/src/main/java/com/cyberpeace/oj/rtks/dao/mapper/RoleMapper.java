package com.cyberpeace.oj.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.dao.entity.Role;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

	IPage<Role> listRole(Page<Role> page, @Param("pattern") String pattern);

	/**
	 * 获取绑定在指定层级下的岗位列表
	 */
	List<Role> getRolesUnderLevel(String levelId);

}
