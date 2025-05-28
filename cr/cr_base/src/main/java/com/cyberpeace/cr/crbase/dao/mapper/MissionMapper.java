/**
 * 这段代码定义了一个基于 MyBatis 的 `MissionMapper` 接口，用于操作数据库中的 `Mission` 实体类。以下是简要分析： 
 * 1. **接口定义**： 
 * - `MissionMapper` 继承了 `BaseMapper<Mission>`，这意味着它已经具备了一些通用的 CRUD 操作功能。 
 * - 使用了 `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * 2. **方法说明**： 
 * - `batchSelect(List<String> resourceIds)`：根据资源 ID 列表批量查询任务。 
 * - `selectByRoomId(String roomId)`：通过房间 ID 查询任务列表。 
 * - `batchInsert(List<Mission> missions)`：批量插入任务。 
 * - `existByTitleAndIds(String title, List<String> missionIds)`：检查是否存在符合特定标题和 ID 列表的任务。 
 * 3. **注解与作用域**： 
 * - `@Repository` 注解表明该接口是一个 Spring 容器管理的 Bean，通常用于数据访问层。 
 * 总结：此代码提供了一组针对 `Mission` 实体的操作方法，支持批量操作和特定条件查询，是典型的 MyBatis 数据访问接口实现。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

/**
 * 任务Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface MissionMapper extends BaseMapper<Mission> {

	/**
	 * 批量查询
	 *
	 * @param resourceIds 资源id列表
	 * @return {@code List<Mission> }
	 */
	List<Mission> batchSelect(List<String> resourceIds);

	/**
	 * 查询由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<Mission> }
	 */
	List<Mission> selectByRoomId(String roomId);

	/**
	 * 批量插入
	 *
	 * @param missions 任务
	 */
	void batchInsert(List<Mission> missions);

	/**
	 * 存在由标题和id列表
	 *
	 * @param title      标题
	 * @param missionIds 任务id列表
	 * @return {@code Boolean }
	 */
	Boolean existByTitleAndIds(@Param("title") String title,
			@Param("missionIds") List<String> missionIds);
}
