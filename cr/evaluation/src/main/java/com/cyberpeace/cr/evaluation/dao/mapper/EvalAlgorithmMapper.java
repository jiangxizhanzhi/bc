/**
 * 这段代码定义了一个名为 `EvalAlgorithmMapper` 的接口，该接口继承自 `BaseMapper<EvalAlgorithm>`。这个接口主要用于数据库操作，与 `EvalAlgorithm` 实体类进行交互。以下是对这段代码的简要分析： 
 * ### 1. 包和导入声明 
 * - 代码位于 `com.cyberpeace.cr.evaluation.dao.mapper` 包下。 
 * - 导入了必要的类，如 `BaseMapper`、`IPage` 和 `Page`（来自 MyBatis Plus），以及 `EvalAlgorithm` 实体类。 
 * ### 2. 接口注解 
 * - `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository` 注解表明这是一个 Spring 的数据访问对象（DAO）组件。 
 * ### 3. 方法定义 
 * #### 3.1 更新状态 
 * - `void updateStateById(String resourceId);`：根据资源ID更新状态。 
 * - `void updateStateByIds(List<String> algorithmIds);`：根据算法ID列表更新状态。 
 * #### 3.2 查询方法 
 * - `List<EvalAlgorithm> selectByType(@Param("type") String type);`：根据类型查询算法列表。 
 * - `List<EvalAlgorithm> getAll();`：获取所有算法列表。 
 * #### 3.3 存在性检查 
 * - `boolean existByName(String name, String resourceId);`：检查是否存在指定名称的算法。 
 * #### 3.4 分页查询 
 * - `IPage<EvalAlgorithm> listEvalAlgorithm(Page page, EvalListQuery listQuery);`：分页查询算法列表。 
 * #### 3.5 删除方法 
 * - `void deleteAll(List<String> algorithmIds);`：根据算法ID列表删除算法。 
 * - `void deleteAllUnUsedBackup(List<String> algorithmIds);`：删除未使用的备份算法。 
 * ### 4. 总结 
 * - `EvalAlgorithmMapper` 接口提供了多种数据库操作方法，包括查询、更新、存在性检查和删除。 
 * - 这些方法涵盖了常见的 CRUD 操作，并且支持分页查询和批量操作。 
 * - 通过继承 `BaseMapper<EvalAlgorithm>`，可以利用 MyBatis Plus 提供的默认实现来简化开发。 
 * 这段代码展示了如何使用 MyBatis Plus 和 Spring 来实现一个功能完善的数据库访问层。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评估算法Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalAlgorithmMapper extends BaseMapper<EvalAlgorithm> {

	/**
	 * 更新州由id
	 *
	 * @param resourceId 资源id
	 */
	void updateStateById(String resourceId);

	/**
	 * 查询由类型
	 *
	 * @param type 类型
	 * @return {@code List<EvalAlgorithm> }
	 */
	List<EvalAlgorithm> selectByType(@Param("type") String type);

	/**
	 * 存在由名称
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	boolean existByName(String name, String resourceId);

	/**
	 * 列出 eval 算法
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalAlgorithm> }
	 */
	IPage<EvalAlgorithm> listEvalAlgorithm(Page page, EvalListQuery listQuery);

	/**
	 * 更新州由id列表
	 *
	 * @param algorithmIds 算法id列表
	 */
	void updateStateByIds(List<String> algorithmIds);

	/**
	 * 获取都
	 *
	 * @return {@code List<EvalAlgorithm> }
	 */
	List<EvalAlgorithm> getAll();

	/**
	 * 全部删除
	 *
	 * @param algorithmIds 算法id列表
	 */
	void deleteAll(List<String> algorithmIds);

	/**
	 * 删除所有未使用备份
	 *
	 * @param algorithmIds 算法id列表
	 */
	void deleteAllUnUsedBackup(List<String> algorithmIds);
}




