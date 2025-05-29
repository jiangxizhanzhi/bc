package com.cyberpeace.cs.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 当地场景Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `LocalSceneMapper` 的 MyBatis Mapper 接口，用于操作数据库中的 `LocalScene` 实体类。以下是代码的简要分析： 
 * 1. **接口定义**： 
 * - `LocalSceneMapper` 继承了 `BaseMapper<LocalScene>`，这意味着它已经具备了基本的 CRUD 操作功能。 
 * - 使用了 `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository` 注解将其标记为 Spring 容器中的一个 Bean。 
 * 2. **方法说明**： 
 * - `getByBusinessInstanceId(String businessInstanceId)`：根据业务实例 ID 查询对应的 `LocalScene` 对象。 
 * - `updateEndTime(LocalScene localScene)`：更新指定 `LocalScene` 对象的销毁时间，即使时间为空也会进行更新。 
 * - `selectByCloseTime(Date closeTime)`：查询在指定关闭时间的 `LocalScene` 列表。 
 * - `selectBetweenEndTime(Date startTime, Date endTime)`：查询在指定时间段内的 `LocalScene` 列表。 
 * - `selectBySceneId(String sceneId)` 和 `selectByBusinessInstanceId(String businessInstanceId)`：分别通过场景 ID 和业务实例 ID 查询对应的 `LocalScene` 对象。 
 * - `selectAll()`：查询所有的 `LocalScene` 记录。 
 * 3. **注解使用**： 
 * - `@Param` 注解用于指定参数名称，当方法参数多于一个时需要使用此注解来明确参数对应关系。 
 * 总结来说，这个接口提供了一系列针对 `LocalScene` 实体的操作方法，涵盖了常见的查询、更新等数据库操作需求。 
**/
@Mapper
@Repository
public interface LocalSceneMapper extends BaseMapper<LocalScene> {

	/**
	 * 获取由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene }
	 */
	LocalScene getByBusinessInstanceId(String businessInstanceId);

	/**
	 * 修改销毁时间 空数据也修改
	 *
	 * @param localScene 当地场景
	 */
	void updateEndTime(LocalScene localScene);

	/**
	 * 查询到关闭时间的场景
	 *
	 * @param closeTime
	 * @return
	 */
	List<LocalScene> selectByCloseTime(Date closeTime);

	/**
	 * 查询结束时在一定范围内的场景
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<LocalScene> selectBetweenEndTime(@Param("start_time") Date startTime,
			@Param("end_time") Date endTime);

	/**
	 * 查询由场景id
	 *
	 * @param sceneId 场景id
	 * @return {@code LocalScene }
	 */
	LocalScene selectBySceneId(@Param("sceneId") String sceneId);

	/**
	 * 查询都
	 *
	 * @return {@code List<LocalScene> }
	 */
	List<LocalScene> selectAll();

	/**
	 * 查询通过业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene}
	 */
	LocalScene selectByBusinessInstanceId(@Param("businessInstanceId") String businessInstanceId);
}

