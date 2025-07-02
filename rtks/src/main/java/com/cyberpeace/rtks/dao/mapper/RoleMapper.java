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
 * 岗位Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 列表岗位
	 *
	 * @param page    分页
	 * @param pattern 模式
	 * @param frameId 框架id
	 * @return {@code IPage<Role> }
	 * @throws
	 * @see Page<Role>
	 * @see String
	 * @see String
	 * @see IPage<Role>
	 */
	IPage<Role> listRole(Page<Role> page, @Param("pattern") String pattern, String frameId);

	/**
	 * 获取绑定在指定层级下的岗位列表
	 *
	 * @param levelId 水平id
	 * @return {@code List<Role> }
	 * @throws
	 * @see String
	 * @see List<Role>
	 */
	List<Role> getRolesUnderLevel(String levelId);

	/**
	 * 查询岗位由框架id
	 *
	 * @param frameId 框架id
	 * @return {@code List<Role> }
	 * @throws
	 * @see String
	 * @see List<Role>
	 */
	List<Role> selectRoleByFrameId(@Param("frameId") String frameId);

	/**
	 * 获取岗位id列表由水平id列表
	 *
	 * @param levelIds 水平id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getRoleIdsByLevelIds(List<String> levelIds);

	/**
	 * 查询水平由岗位id
	 *
	 * @param resource_id 资源id
	 * @return {@code List<Level> }
	 * @throws
	 * @see String
	 * @see List<Level>
	 */
	List<Level> selectLevelsByRoleId(String resource_id);

	/**
	 * 删除全部
	 *
	 * @param table 桌子
	 * @throws
	 * @see String
	 */
	void removeAll(String table);

	/**
	 * 从预设复制
	 *
	 * @param table 桌子
	 * @throws
	 * @see String
	 */
	void copyFromPreset(String table, String columns);

	/**
	 * 获取表字段
	 *
	 * @param table 表明
	 * @throws
	 * @see String
	 */
	String getTableColumns(String table);

	/**
	 * 查询岗位列表由id列表
	 *
	 * @param ids  id列表
	 * @param type 类型
	 * @return {@code List<Role> }
	 * @throws
	 * @see List<String>
	 * @see String
	 * @see List<Role>
	 */
	List<Role> selectRoleListByIds(@Param("ids") List<String> ids, @Param("type") String type);

	/**
	 * 清除岗位
	 *
	 * @throws
	 */
	void clearRole();

	/**
	 * 如果名称重复
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean ifNameDuplicate(@Param("name") String name, @Param("resourceId") String resourceId);
}
