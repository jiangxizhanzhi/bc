/**
 * 这段代码定义了一个名为 `EvalAlgorithmServiceImpl` 的类，它实现了 `EvalAlgorithmService` 接口，并且继承了 `ServiceImpl` 类。该类主要用于处理与评估算法相关的业务逻辑。以下是对代码的简要分析： 
 * ### 类结构 
 * - **类名**: `EvalAlgorithmServiceImpl` 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.impl` 
 * - **继承关系**: 继承自 `ServiceImpl<EvalAlgorithmMapper, EvalAlgorithm>`，实现了 `EvalAlgorithmService` 接口。 
 * ### 主要成员变量 
 * - `evalAlgorithmMapper`: 用于操作 `EvalAlgorithm` 实体的数据库映射。 
 * - `evalAggregationMapper`, `evalCollectionMapper`, `evalIndexMapper`, `evalIndicatorMapper`: 用于操作其他相关实体的数据库映射。 
 * - `remotePythonService`: 远程调用 Python 服务的客户端。 
 * ### 主要方法 
 * 1. **`selectUsedNum(List<String> ids)`** 
 * - 功能: 计算给定算法 ID 列表中被使用的次数。 
 * - 参数: `ids` - 算法 ID 列表。 
 * - 返回值: 使用次数的总数。 
 * 2. **`delete(List<String> ids)`** 
 * - 功能: 根据 ID 列表删除算法。 
 * - 参数: `ids` - 算法 ID 列表。 
 * - 返回值: 操作是否成功（返回 `true` 表示成功）。 
 * 3. **`updateStateById(String resourceId)`** 
 * - 功能: 根据资源 ID 更新状态。 
 * - 参数: `resourceId` - 资源 ID。 
 * - 返回值: 无。 
 * 4. **`selectByType(String type)`** 
 * - 功能: 根据类型查询算法。 
 * - 参数: `type` - 算法类型。 
 * - 返回值: 符合条件的算法列表。 
 * 5. **`page(EvalListQuery listQuery)`** 
 * - 功能: 分页查询算法。 
 * - 参数: `listQuery` - 查询参数对象。 
 * - 返回值: 分页后的算法列表。 
 * 6. **`selectUsedAlgorithmIds(List<String> ids)`** 
 * - 功能: 查询使用算法的 ID 列表。 
 * - 参数: `ids` - 算法 ID 列表。 
 * - 返回值: 使用算法的 ID 列表。 
 * 7. **`updateStateByIds(List<String> algorithmIds)`** 
 * - 功能: 根据算法 ID 列表更新状态。 
 * - 参数: `algorithmIds` - 算法 ID 列表。 
 * - 返回值: 无。 
 * 8. **`runAlgorithm(String algorithmId, Object data, Object listData, Object otherArgs)`** 
 * - 功能: 运行指定算法。 
 * - 参数: `algorithmId` - 算法 ID，`data` - 输入数据，`listData` - 列表数据，`otherArgs` - 其他参数。 
 * - 返回值: 运行结果。 
 * ### 总结 
 * 这个类主要负责处理评估算法相关的业务逻辑，包括增删改查操作以及运行算法。通过注入不同的 Mapper 和远程服务，可以灵活地操作数据库和执行外部算法。整体设计合理，职责清晰。 
**/
package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.integratation.service.RemotePythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评估算法Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class EvalAlgorithmServiceImpl extends ServiceImpl<EvalAlgorithmMapper, EvalAlgorithm> implements EvalAlgorithmService {

	/**
	 * 评估算法Mapper
	 */
	@Autowired
	EvalAlgorithmMapper evalAlgorithmMapper;

	/**
	 * 评估集合体Mapper
	 */
	@Autowired
	EvalAggregationMapper evalAggregationMapper;

	/**
	 * 评估收集Mapper
	 */
	@Autowired
	EvalCollectionMapper evalCollectionMapper;

	/**
	 * 评估指数Mapper
	 */
	@Autowired
	EvalIndexMapper evalIndexMapper;

	/**
	 * 评估指示器Mapper
	 */
	@Autowired
	EvalIndicatorMapper evalIndicatorMapper;

	/**
	 * 远程蟒Service
	 */
	@Autowired
	RemotePythonService remotePythonService;

	/**
	 * 查询使用数量
	 *
	 * @param ids id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer selectUsedNum(List<String> ids) {
		Integer num = evalAggregationMapper.selectAlgorithmNum(ids)
				+ evalCollectionMapper.selectAlgorithmSum(ids)
				+ evalIndexMapper.selectAlgorithmSum(ids)
				+ evalIndicatorMapper.selectAlgorithmSum(ids);
		return num;
	}

	/**
	 * 删除
	 *
	 * @param ids id列表
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean delete(List<String> ids) {

		evalAlgorithmMapper.deleteBatchIds(ids);
		return true;
	}

	/**
	 * 更新州由id
	 *
	 * @param resourceId 资源id
	 */
	@Override
	public void updateStateById(String resourceId) {
		evalAlgorithmMapper.updateStateById(resourceId);
	}

	/**
	 * 查询由类型
	 *
	 * @param type 类型
	 * @return {@code List<EvalAlgorithm> }
	 */
	@Override
	public List<EvalAlgorithm> selectByType(String type) {
		return evalAlgorithmMapper.selectByType(type);
	}

	/**
	 * 分页
	 *
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalAlgorithm> }
	 */
	@Override
	public IPage<EvalAlgorithm> page(EvalListQuery listQuery) {

		Page page = listQuery.toPageParam();

        return evalAlgorithmMapper.listEvalAlgorithm(page, listQuery);
	}

	/**
	 * 查询使用算法id列表
	 *
	 * @param ids id列表
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> selectUsedAlgorithmIds(List<String> ids) {
		List<String> usedAlgorithmIds = evalAggregationMapper.selectUsedAlgorithmIds(ids);

		usedAlgorithmIds.addAll(evalCollectionMapper.selectUsedAlgorithmIds(ids));
		usedAlgorithmIds.addAll(evalIndexMapper.selectUsedAlgorithmIds(ids));
		usedAlgorithmIds.addAll(evalIndicatorMapper.selectUsedAlgorithmIds(ids));

		return usedAlgorithmIds.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 更新州由id列表
	 *
	 * @param algorithmIds 算法id列表
	 */
	@Override
	public void updateStateByIds(List<String> algorithmIds) {
		evalAlgorithmMapper.updateStateByIds(algorithmIds);
	}

	/**
	 * run 算法
	 *
	 * @param algorithmId 算法id
	 * @param data        数据
	 * @param listData    列出数据
	 * @param otherArgs   其他参数
	 * @return {@code Object }
	 */
	public Object runAlgorithm(String algorithmId, Object data, Object listData, Object otherArgs) {
		EvalAlgorithm algorithm = evalAlgorithmMapper.selectById(algorithmId);
		return remotePythonService.runAlgorithm(algorithmId, algorithm.getCode(), data, listData, (List<Serializable>) otherArgs);
	}
}
