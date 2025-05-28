/**
 * ### 代码分析 
 * #### 文件概述 
 * 该文件定义了一个名为 `MissionTemplateMapper` 的接口，位于 `com.cyberpeace.cr.crbase.dao.mapper` 包中。这是一个基于 MyBatis 和 MyBatis-Plus 的数据访问对象（DAO）接口，用于操作数据库中的 `MissionTemplate` 表。 
 * #### 主要功能 
 * 以下是接口的主要功能模块及其描述： 
 * 1. **批量插入** 
 * - 方法名：`batchInsert` 
 * - 功能：将一个 `List<MissionTemplate>` 对象列表批量插入到数据库中。 
 * - 参数： 
 * - `missionTemplates`：需要插入的任务模板列表。 
 * - 返回值：无。 
 * 2. **批量更新业务模板ID** 
 * - 方法名：`batchUpdateBusinessTemplateId` 
 * - 功能：根据业务模板ID和任务模板ID列表批量更新数据库中任务模板的业务模板ID。 
 * - 参数： 
 * - `businessTemplateId`：新的业务模板ID。 
 * - `missionTemplateIds`：需要更新的任务模板ID列表。 
 * - 返回值：无。 
 * 3. **分页查询** 
 * - 方法名：`pageWithBusiness` 
 * - 功能：根据分页参数、任务类型、难度和模式进行分页查询，并返回结果集。 
 * - 参数： 
 * - `page`：分页对象。 
 * - `type`：任务类型。 
 * - `difficulty`：任务难度。 
 * - `pattern`：任务模式。 
 * - 返回值：分页结果集，类型为 `IPage<MissionTemplateRes>`。 
 * 4. **列表查询** 
 * - 方法名：`listWithBusiness` 
 * - 功能：根据任务模板ID列表查询对应的业务结果集。 
 * - 参数： 
 * - `missionTemplateIds`：任务模板ID列表。 
 * - 返回值：任务模板结果集，类型为 `List<MissionTemplateRes>`。 
 * 5. **验证ID列表** 
 * - 方法名：`validateIds` 
 * - 功能：验证任务模板ID列表的有效性。 
 * - 参数： 
 * - `missionTemplateIds`：任务模板ID列表。 
 * - 返回值：有效的任务模板ID列表，类型为 `List<String>`。 
 * 6. **根据业务模板ID查询** 
 * - 方法名：`selectByBusinessTemplateId` 
 * - 功能：根据业务模板ID查询对应的任务模板ID列表。 
 * - 参数： 
 * - `businessTemplateId`：业务模板ID。 
 * - 返回值：任务模板ID列表，类型为 `List<String>`。 
 * 7. **批量获取带业务信息** 
 * - 方法名：`batchGetWithBusiness` 
 * - 功能：根据任务模板ID列表批量获取任务模板及其业务信息。 
 * - 参数： 
 * - `missionTemplateIds`：任务模板ID列表。 
 * - 返回值：任务模板及其业务信息的结果集，类型为 `List<MissionWithBT>`。 
 * #### 技术特性 
 * - **MyBatis-Plus**：继承了 `BaseMapper` 接口，提供了基础的 CRUD 操作支持。 
 * - **MapStruct**：通过 `@Mapper` 注解表明这是一个 MapStruct 的映射器，用于对象之间的转换。 
 * - **Spring 注解**：使用了 `@Repository` 注解表明这是一个 Spring 的数据访问层组件。 
 * - **分页与插件支持**：通过 `Page` 对象实现分页功能，依赖 MyBatis-Plus 的分页插件。 
 * #### 总结 
 * 该接口主要负责任务模板相关的 CRUD 操作以及复杂的查询逻辑，包括批量操作、分页查询、多条件查询等。它结合了 MyBatis 和 MyBatis-Plus 的强大功能，同时利用了 MapStruct 进行对象映射，适合处理复杂的数据操作场景。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWithBT;

/**
 * 任务模板Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface MissionTemplateMapper extends BaseMapper<MissionTemplate> {

	/**
	 * 批量插入
	 *
	 * @param missionTemplates 任务模板
	 */
	void batchInsert(@Param("missionTemplates") List<MissionTemplate> missionTemplates);

	/**
	 * 批量更新业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @param missionTemplateIds 任务模板id列表
	 */
	void batchUpdateBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 分页跟业务
	 *
	 * @param page       分页
	 * @param type       类型
	 * @param difficulty 难度
	 * @param pattern    模式
	 * @return {@code IPage<MissionTemplateRes> }
	 */
	IPage<MissionTemplateRes> pageWithBusiness(@Param("page") Page page,
			@Param("type") Integer type, @Param("difficulty") Integer difficulty,
			@Param("pattern") String pattern);

	/**
	 * 列表跟业务
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<MissionTemplateRes> }
	 */
	List<MissionTemplateRes>
			listWithBusiness(@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 验证id列表
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<String> }
	 */
	List<String> validateIds(@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 查询由业务模板id
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code List<String> }
	 */
	List<String> selectByBusinessTemplateId(@Param("businessTemplateId") String businessTemplateId);

	/**
	 * 批量获取跟业务
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<MissionWithBT> }
	 */
	List<MissionWithBT>
			batchGetWithBusiness(@Param("missionTemplateIds") List<String> missionTemplateIds);
}
