package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

	IPage<Role> listRole(Page<Role> page, @Param("pattern") String pattern, String frameId);

	/**
	 * 获取绑定在指定层级下的岗位列表
	 */
	List<Role> getRolesUnderLevel(String levelId);

	List<Role> selectRoleByFrameId(@Param("frameId") String frameId);

	List<String> getRoleIdsByLevelIds(List<String> levelIds);

	List<Level> selectLevelsByRoleId(String resourceId);

	void removeAll(String table);

	void copyFromPreset(String table);

	List<Role> selectRoleListByIds(@Param("ids") List<String> ids, @Param("type") String type);

	void clearRole();

	Boolean ifNameDuplicate(@Param("name") String name, @Param("resourceId") String resourceId);
}
