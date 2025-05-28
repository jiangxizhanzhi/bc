/**
 * 这段代码定义了一个名为 `ResourceCollectionMapper` 的接口，该接口继承了 `BaseMapper<ResourceCollection>`。这个接口使用 MyBatis-Plus 框架来操作数据库中的 `ResourceCollection` 实体类。以下是对代码的简要分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.oj.ojbase.dao.mapper; 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection; 
 * import com.cyberpeace.oj.ojbase.service.context.CollectionContext; 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import java.util.List; 
 * ``` 
 * - `com.cyberpeace.oj.ojbase.dao.mapper`：包名，表示这个接口属于 `com.cyberpeace.oj.ojbase.dao.mapper` 包。 
 * - 导入了一些必要的类和注解，包括 MyBatis-Plus 的 `BaseMapper` 和自定义的 `CollectionContext` 类。 
 * ### 接口定义 
 * ```java 
 * @Mapper 
 * public interface ResourceCollectionMapper extends BaseMapper<ResourceCollection> { 
 * ``` 
 * - `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - `extends BaseMapper<ResourceCollection>` 表示该接口继承了 MyBatis-Plus 提供的基本映射功能，可以自动实现一些基本的 CRUD 操作。 
 * ### 方法定义 
 * 接口中定义了多个方法，用于操作 `ResourceCollection` 数据： 
 * 1. **根据唯一索引删除资源收藏数据** 
 * ```java 
 * Integer deleteByUnique(CollectionContext collectionContext); 
 * ``` 
 * - 参数：`CollectionContext collectionContext`，包含删除所需的信息。 
 * - 返回值：`Integer`，表示受影响的行数。 
 * 2. **根据唯一索引查询资源数据是否被收藏** 
 * ```java 
 * ResourceCollection selectByUnique(CollectionContext collectionContext); 
 * ``` 
 * - 参数：`CollectionContext collectionContext`，包含查询所需的信息。 
 * - 返回值：`ResourceCollection` 对象，如果找到则返回，否则返回 `null`。 
 * 3. **查询对应类型的收藏资源** 
 * ```java 
 * List<ResourceCollection> selectByType(@Param("userId") String userId, 
 * @Param("businessType") Integer businessType); 
 * ``` 
 * - 参数： 
 * - `@Param("userId") String userId`：用户ID。 
 * - `@Param("businessType") Integer businessType`：业务类型。 
 * - 返回值：`List<ResourceCollection>`，包含符合条件的资源集合。 
 * 4. **根据业务数据查询收藏** 
 * ```java 
 * List<String> selectByBusiness(@Param("userId") String userId, 
 * @Param("businessId") String businessId, 
 * @Param("businessType") Integer businessType); 
 * ``` 
 * - 参数： 
 * - `@Param("userId") String userId`：用户ID。 
 * - `@Param("businessId") String businessId`：业务ID。 
 * - `@Param("businessType") Integer businessType`：业务类型。 
 * - 返回值：`List<String>`，包含符合条件的资源ID列表。 
 * 5. **查询由业务id列表** 
 * ```java 
 * List<ResourceCollection> selectByBusinessIds(@Param("userId") String userId, 
 * @Param("businessType") Integer businessType, 
 * @Param("businessIds") List<String> businessIds); 
 * ``` 
 * - 参数： 
 * - `@Param("userId") String userId`：用户ID。 
 * - `@Param("businessType") Integer businessType`：业务类型。 
 * - `@Param("businessIds") List<String> businessIds`：业务ID列表。 
 * - 返回值：`List<ResourceCollection>`，包含符合条件的资源集合。 
 * ### 总结 
 * - `ResourceCollectionMapper` 是一个 MyBatis-Plus 的 Mapper 接口，用于操作 `ResourceCollection` 实体类。 
 * - 该接口提供了多种方法来执行特定的查询和删除操作。 
 * - 这些方法覆盖了常见的业务需求，如根据唯一索引删除或查询资源，以及根据不同类型或业务ID查询资源等。 
**/
package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源收集Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface ResourceCollectionMapper extends BaseMapper<ResourceCollection> {

	/**
	 * 根据唯一索引删除资源收藏数据
	 *
	 * @param collectionContext
	 * @return
	 * @throws
	 * @see CollectionContext
	 * @see Integer
	 */
	Integer deleteByUnique(CollectionContext collectionContext);

	/**
	 * 根据唯一索引查询资源数据是否被收藏
	 *
	 * @param collectionContext
	 * @return
	 * @throws
	 * @see CollectionContext
	 * @see ResourceCollection
	 */
	ResourceCollection selectByUnique(CollectionContext collectionContext);

	/**
	 * 查询对应类型的收藏资源
	 *
	 * @param userId
	 * @param businessType
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<ResourceCollection>
	 */
	List<ResourceCollection> selectByType(@Param("userId") String userId,
			@Param("businessType") Integer businessType);

	/**
	 * 根据业务数据查询收藏
	 *
	 * @param userId       用户id
	 * @param businessId
	 * @param businessType
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see List<String>
	 */
	List<String> selectByBusiness(@Param("userId") String userId,
								  @Param("businessId") String businessId, @Param("businessType") Integer businessType);

	/**
	 * 查询由业务id列表
	 *
	 * @param userId       用户id
	 * @param businessType 业务类型
	 * @param businessIds  业务id列表
	 * @return {@code List<ResourceCollection> }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<String>
	 * @see List<ResourceCollection>
	 */
	List<ResourceCollection> selectByBusinessIds(@Param("userId") String userId, @Param("businessType")Integer businessType,
														@Param("businessIds") List<String> businessIds);
}
