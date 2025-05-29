package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 评估指示器Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalIndicatorMapper` 的接口，该接口继承了 `BaseMapper<EvalIndicator>`。`BaseMapper` 是 MyBatis-Plus 提供的一个基础接口，用于简化常见的 CRUD 操作。以下是代码的详细分析： 
 * ### 包和导入声明 
 * - **包声明**：`com.cyberpeace.cr.evaluation.dao.mapper` 
 * - **导入的类和接口**： 
 * - `BaseMapper` 和 `IPage` 来自 MyBatis-Plus。 
 * - `Page` 用于分页操作。 
 * - `EvalIndicator` 是数据实体类。 
 * - `EvalListQuery` 是一个用于查询列表的上下文对象。 
 * ### 注解 
 * - `@Mapper`：表示这是一个 MyBatis 的 Mapper 接口，MyBatis 会自动扫描并注册这个接口。 
 * ### 方法定义 
 * 1. **`selectAlgorithmSum`** 
 * - **描述**：根据给定的算法 ID 列表计算总和。 
 * - **参数**：`List<String> algorithmIds` 
 * - **返回值**：`Integer` 
 * 2. **`listEvalIndicator`** 
 * - **描述**：列出评估指标，并支持分页查询。 
 * - **参数**：`Page page`（分页信息），`EvalListQuery listQuery`（查询条件） 
 * - **返回值**：`IPage<EvalIndicator>`（分页后的评估指标列表） 
 * 3. **`existByName`** 
 * - **描述**：检查是否存在具有特定名称和资源 ID 的评估指标。 
 * - **参数**：`String name`（名称），`String resourceId`（资源 ID） 
 * - **返回值**：`boolean` 
 * 4. **`selectUsedAlgorithmIds`** 
 * - **描述**：查询已使用的算法 ID 列表。 
 * - **参数**：`List<String> algorithmIds` 
 * - **返回值**：`List<String>` 
 * 5. **`selectIndexUsedNum`** 
 * - **描述**：查询指定 ID 列表中使用的评估指标的数量。 
 * - **参数**：`List<String> ids` 
 * - **返回值**：`Integer` 
 * ### 总结 
 * `EvalIndicatorMapper` 接口提供了多个方法来处理评估指标相关的操作，包括分页查询、存在性检查、统计等。这些方法使得对 `EvalIndicator` 实体的操作更加方便和高效。通过继承 `BaseMapper`，这些方法可以利用 MyBatis-Plus 提供的默认实现，从而减少手动编写 SQL 的工作量。 
**/
@Mapper
public interface EvalIndicatorMapper extends BaseMapper<EvalIndicator> {

	/**
	 * 查询算法和
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code Integer }
	 */
	Integer selectAlgorithmSum(List<String> algorithmIds);

	/**
	 * 列出评估指标
	 *
	 * @param page      分页
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalIndicator> }
	 */
	IPage<EvalIndicator> listEvalIndicator(Page page, EvalListQuery listQuery);

	/**
	 * 存在由名称
	 *
	 * @param name       名称
	 * @param resourceId 资源id
	 * @return boolean
	 */
	boolean existByName(String name, String resourceId);

	/**
	 * 查询使用算法id列表
	 *
	 * @param algorithmIds 算法id列表
	 * @return {@code List<String> }
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);

	/**
	 * 查询指数使用数量
	 *
	 * @param ids id列表
	 * @return {@code Integer }
	 */
	Integer selectIndexUsedNum(List<String> ids);
}

