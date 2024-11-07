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

/**
 * 角色Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 列表角色
	 *
	 * @param page    分页
	 * @param pattern 模式
	 * @param frameId 框架id
	 * @return {@code IPage<Role> }
	 */
	IPage<Role> listRole(Page<Role> page, @Param("pattern") String pattern, String frameId);

	/**
	 * 获取绑定在指定层级下的岗位列表
	 *
	 * @param levelId 水平id
	 * @return {@code List<Role> }
	 */
	List<Role> getRolesUnderLevel(String levelId);

	/**
	 * 查询角色由框架id
	 *
	 * @param frameId 框架id
	 * @return {@code List<Role> }
	 */
	List<Role> selectRoleByFrameId(@Param("frameId") String frameId);

	/**
	 * 获取角色id列表由水平id列表
	 *
	 * @param levelIds 水平id列表
	 * @return {@code List<String> }
	 */
	List<String> getRoleIdsByLevelIds(List<String> levelIds);

	/**
	 * 查询水平由角色id
	 *
	 * @param resource_id 资源id
	 * @return {@code List<Level> }
	 */
	List<Level> selectLevelsByRoleId(String resource_id);

	/**
	 * 全部删除
	 *
	 * @param table 桌子
	 */
	void removeAll(String table);

	/**
	 * 从预设复制
	 *
	 * @param table 桌子
	 */
	void copyFromPreset(String table);

	/**
	 * 查询角色列表由id列表
	 *
	 * @param ids  id列表
	 * @param type 类型
	 * @return {@code List<Role> }
	 */
	List<Role> selectRoleListByIds(@Param("ids") List<String> ids, @Param("type") String type);

	/**
	 * 明确角色
	 */
	void clearRole();

	/**
	 * 如果名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return {@code Boolean }
	 */
	Boolean ifNameDuplicate(@Param("name") String name, @Param("resourceId") String resourceId);
}
