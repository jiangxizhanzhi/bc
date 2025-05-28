/**
 * 这段代码定义了一个名为 `EvalAggregationMapper` 的接口，该接口继承了 `BaseMapper<EvalAggregation>`。这个接口用于与数据库中的 `EvalAggregation` 实体进行交互，并提供了几个自定义的方法来执行特定的查询操作。 
 * ### 主要内容分析： 
 * 1. **包声明**： 
 * - 代码位于 `com.cyberpeace.cr.evaluation.dao.mapper` 包中。 
 * 2. **导入的类**： 
 * - `BaseMapper`：来自 `com.baomidou.mybatisplus.core.mapper` 包，这是一个 MyBatis-Plus 提供的基础映射接口。 
 * - `EvalAggregation`：是定义在 `com.cyberpeace.cr.evaluation.dao.entity` 包中的实体类。 
 * - `List`：Java 标准库中的泛型接口，用于表示列表。 
 * - `Integer` 和 `String`：基本数据类型的包装类。 
 * 3. **注解**： 
 * - `@Mapper`：表示该接口是一个 MyBatis 的 Mapper 接口，用于简化 MyBatis 的配置。 
 * 4. **接口方法**： 
 * - `selectIdsByEvalIndicatorIds`：根据给定的评估指示器 ID 列表查询 `EvalAggregation` 实体列表。 
 * - `selectAlgorithmNum`：根据给定的算法 ID 列表查询算法的数量。 
 * - `selectUsedAlgorithmIds`：根据给定的算法 ID 列表查询使用的算法 ID 列表。 
 * ### 总结： 
 * - `EvalAggregationMapper` 接口扩展了 `BaseMapper`，这使得它可以利用 MyBatis-Plus 提供的基本 CRUD 操作。 
 * - 除了这些基础操作外，还定义了三个额外的方法来满足特定的查询需求。 
 * - 这些方法通过传入不同的参数来实现不同的查询逻辑，例如根据评估指示器 ID 或算法 ID 来获取相应的数据。 
 * 这个接口的设计使得数据访问层可以更加灵活地处理各种查询需求，同时利用 MyBatis-Plus 提供的功能来减少样板代码。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 评估集合体Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface EvalAggregationMapper extends BaseMapper<EvalAggregation> {

	/**
	 * 查询id列表由评估指示器id列表
	 *
	 * @param evalIndicatorIds 评估指示器id列表
	 * @return {@code List<EvalAggregation> }
	 */
	List<EvalAggregation> selectIdsByEvalIndicatorIds(List<String> evalIndicatorIds);

	/**
	 * 查询算法数量
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code Integer }
	 */
	Integer selectAlgorithmNum(List<String> algorithmIds);

	/**
	 * 查询使用算法id列表
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code List<String> }
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




