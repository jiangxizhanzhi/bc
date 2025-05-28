/**
 * 这段代码定义了一个名为 `EvalCollectionMapper` 的接口，它继承了 `BaseMapper<EvalCollection>`。这个接口主要用于数据库操作，具体来说是与 `EvalCollection` 实体类相关的 CRUD 操作。以下是对该代码的简要分析： 
 * ### 主要内容 
 * 1. **接口定义** 
 * - `EvalCollectionMapper` 接口继承自 `BaseMapper<EvalCollection>`，这意味着它已经包含了基本的增删改查（CRUD）方法。 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis 的 Mapper 接口。 
 * 2. **自定义方法** 
 * - `selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds)`：根据评估指示器ID列表查询对应的 `EvalCollection` 列表。 
 * - `selectAlgorithmSum(List<String> algorithmIds)`：根据算法ID列表查询算法总和。 
 * - `getMaxId()`：获取最大ID。 
 * - `selectUsedAlgorithmIds(List<String> algorithmIds)`：根据算法ID列表查询使用的算法ID列表。 
 * - `selectBythirdIndexId(String indexId)`：根据指数ID查询对应的 `EvalCollection` 列表。 
 * - `selectByEvalIndicatorIds(List<String> evalIndicatorIds)`：根据评估指示器ID列表查询 `EvalCollectionContextRsp` 列表。 
 * ### 分析 
 * - **继承关系**：`EvalCollectionMapper` 继承自 `BaseMapper<EvalCollection>`，这意味着它已经具备了一些基本的数据库操作方法，如 `selectById`, `insert`, `updateById`, `deleteById` 等。 
 * - **自定义方法**：除了继承的基本方法外，还定义了一些自定义的方法来满足特定的业务需求。这些方法主要涉及根据不同的ID列表查询相关数据。 
 * - **返回类型多样性**：返回类型包括 `List<EvalCollection>`, `Integer`, `List<String>`, 和 `List<EvalCollectionContextRsp>`，展示了不同场景下的数据处理需求。 
 * - **参数类型**：大部分方法的参数类型为 `List<String>`，这表明这些方法可能需要处理多个ID的情况。 
 * ### 总结 
 * `EvalCollectionMapper` 是一个用于处理 `EvalCollection` 实体类的 MyBatis Mapper 接口。它不仅继承了基本的 CRUD 方法，还提供了一些自定义方法以满足特定的业务需求。这些自定义方法主要涉及根据不同的ID列表查询相关的 `EvalCollection` 数据。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCollectionContextRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 评估收集Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface EvalCollectionMapper extends BaseMapper<EvalCollection> {

	/**
	 * 查询id列表由评估指示器id列表
	 *
	 * @param evalIndicatorIds 评估指示器id列表
	 * @return {@code List<EvalCollection> }
	 */
	List<EvalCollection> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	/**
	 * 查询算法和
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code Integer }
	 */
	Integer selectAlgorithmSum(List<String> algorithmIds);

	/**
	 * 获取麦克斯id
	 *
	 * @return {@code Integer }
	 */
	Integer getMaxId();

	/**
	 * 查询使用算法id列表
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code List<String> }
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);

	/**
	 * 查询byThird指数id
	 *
	 * @param indexId 指数id
	 * @return {@code List<EvalCollection> }
	 */
	List<EvalCollection> selectBythirdIndexId(String indexId);

	/**
	 * 查询由评估指示器id列表
	 *
	 * @param evalIndicatorIds 评估指示器id列表
	 * @return {@code List<EvalCollectionContextRsp> }
	 */
	List<EvalCollectionContextRsp> selectByEvalIndicatorIds(List<String> evalIndicatorIds);
}




