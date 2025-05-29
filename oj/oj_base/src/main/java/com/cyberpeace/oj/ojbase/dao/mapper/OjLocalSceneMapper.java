package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OJ当地场景Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `OjLocalSceneMapper` 的接口，它继承自 `BaseMapper<OjLocalScene>`。这个接口用于与数据库交互，操作 `OjLocalScene` 实体类对应的数据表。以下是对其主要功能和方法的简要分析： 
 * ### 主要内容 
 * - **继承关系**: `OjLocalSceneMapper` 继承自 `BaseMapper<OjLocalScene>`，这意味着它已经具备了一些基本的 CRUD 操作（如 `selectById`, `insert`, `updateById`, `deleteById` 等）。 
 * - **注解**: 
 * - `@Mapper`: 这个注解表明这是一个 MyBatis 的 Mapper 接口，MyBatis 会自动扫描并注册这个接口。 
 * ### 方法列表 
 * 1. **`selectPrivateScene`** 
 * - 功能: 根据用户 ID、业务 ID 和是否为 Web 场景来查询一个私有场景。 
 * - 参数: 
 * - `userId`: 用户 ID。 
 * - `businessId`: 业务 ID。 
 * - `isWeb`: 是否为 Web 场景。 
 * - 返回值: `OjLocalScene` 对象。 
 * 2. **`selectPublicScene`** 
 * - 功能: 根据业务 ID、是否共享和是否为 Web 场景来查询一个共有场景。 
 * - 参数: 
 * - `businessId`: 业务 ID。 
 * - `shared`: 是否共享。 
 * - `isWeb`: 是否为 Web 场景。 
 * - 返回值: `OjLocalScene` 对象。 
 * 3. **`selectGroupScene`** 
 * - 功能: 根据小组 ID 来查询一个课堂组场景。 
 * - 参数: 
 * - `groupId`: 小组 ID。 
 * - 返回值: `OjLocalScene` 对象。 
 * 4. **`selectByUser`** 
 * - 功能: 根据用户 ID 查询该用户启动的场景数量。 
 * - 参数: 
 * - `userId`: 用户 ID。 
 * - 返回值: 启动场景的数量（`Integer` 类型）。 
 * 5. **`selectByBusiness`** 
 * - 功能: 根据业务 ID 和是否从 Web 启动查询场景列表。 
 * - 参数: 
 * - `businessId`: 业务 ID。 
 * - `fromWeb`: 是否从 Web 启动。 
 * - 返回值: `List<OjLocalScene>` 对象列表。 
 * 6. **`selectByGroupIds`** 
 * - 功能: 根据多个小组 ID 查询对应的场景列表。 
 * - 参数: 
 * - `groupIds`: 小组 ID 列表。 
 * - 返回值: `List<OjLocalScene>` 对象列表。 
 * ### 总结 
 * `OjLocalSceneMapper` 提供了多种方法来根据不同的条件查询 `OjLocalScene` 实体对象及其相关数据。这些方法涵盖了私有场景、共有场景、课堂组场景等不同类型的查询需求，并且支持按用户、业务、小组等多种维度进行查询。这些功能使得应用程序能够方便地获取和管理场景相关的数据。 
**/
@Mapper
public interface OjLocalSceneMapper extends BaseMapper<OjLocalScene> {

	/**
	 * 查询私有场景
	 *
	 * @param userId
	 * @param businessId
	 * @param isWeb
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 * @see OjLocalScene
	 */
	OjLocalScene selectPrivateScene(@Param("userId") String userId,
			@Param("businessId") String businessId, @Param("isWeb") Boolean isWeb);

	/**
	 * 查询共有场景
	 *
	 * @param businessId
	 * @param shared
	 * @param isWeb
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see Boolean
	 * @see OjLocalScene
	 */
	OjLocalScene selectPublicScene(@Param("practiceId") String businessId,
			@Param("shared") Boolean shared, @Param("isWeb") Boolean isWeb);

	/**
	 * 查询课堂组场景
	 *
	 * @param groupId
	 * @return
	 * @throws
	 * @see String
	 * @see OjLocalScene
	 */
	OjLocalScene selectGroupScene(@Param("groupId") String groupId);

	/**
	 * 根据用户查询场景启动数量
	 *
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer selectByUser(@Param("userId") String userId);

	/**
	 * 查询资源启动的场景
	 *
	 * @param businessId
	 * @param fromWeb
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see List<OjLocalScene>
	 */
	List<OjLocalScene> selectByBusiness(@Param("businessId") String businessId,
			@Param("fromWeb") Boolean fromWeb);

	/**
	 * 查询课堂组场景
	 *
	 * @param groupIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see List<OjLocalScene>
	 */
	List<OjLocalScene> selectByGroupIds(@Param("groupIds") List<String> groupIds);
}

