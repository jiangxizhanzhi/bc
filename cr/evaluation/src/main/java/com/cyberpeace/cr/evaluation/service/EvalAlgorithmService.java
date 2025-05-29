package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估算法Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalAlgorithmService` 的接口，该接口扩展了 `IService<EvalAlgorithm>` 接口。这个服务接口用于处理与评估算法相关的业务逻辑。以下是对该接口的简要分析： 
 * ### 主要功能 
 * - **评估算法管理**：提供了一系列方法来管理和操作评估算法。 
 * ### 方法概述 
 * 1. **查询已使用的算法数量** 
 * - `Integer selectUsedNum(List<String> ids);` 
 * - 输入参数是一个包含算法ID的列表，返回这些算法中已被使用的数量。 
 * 2. **批量删除算法** 
 * - `Boolean delete(List<String> ids);` 
 * - 输入参数是一个包含算法ID的列表，返回一个布尔值表示是否删除成功。 
 * 3. **更新算法状态** 
 * - `void updateStateById(String resourceId);` 
 * - 输入参数是一个资源ID，用于更新特定算法的状态（例如启用或禁用）。 
 * 4. **通过类型查询算法** 
 * - `List<EvalAlgorithm> selectByType(String type);` 
 * - 输入参数是一个类型字符串，返回所有匹配该类型的算法列表。 
 * 5. **分页查询算法列表** 
 * - `IPage<EvalAlgorithm> page(EvalListQuery listQuery);` 
 * - 输入参数是一个查询对象，返回一个分页结果集。 
 * 6. **查询已使用的算法ID** 
 * - `List<String> selectUsedAlgorithmIds(List<String> algorithmIds);` 
 * - 输入参数是一个包含算法ID的列表，返回这些算法中已被使用的ID列表。 
 * 7. **更新已使用的算法状态为隐藏** 
 * - `void updateStateByIds(List<String> selectUsedAlgorithmIds);` 
 * - 输入参数是一个包含已使用算法ID的列表，将这些算法的状态更新为隐藏。 
 * 8. **运行算法** 
 * - `Object runAlgorithm(String algorithmId, Object data, Object listData, Object otherArgs);` 
 * - 输入参数包括算法ID、输入数据、列表数据和其他参数，返回算法执行的结果。 
 * ### 总结 
 * 该接口提供了多种方法来管理和操作评估算法，包括查询、删除、更新算法状态、分页查询等。特别地，还提供了一个运行算法的方法，允许根据给定的算法ID和输入参数执行具体的算法逻辑。 
 * 这个接口设计合理，功能全面，能够满足大多数评估算法管理的需求。 
**/
public interface EvalAlgorithmService extends IService<EvalAlgorithm> {

	/**
	 * 查询已使用的算法数量
	 *
	 * @param ids
	 * @return
	 */
	Integer selectUsedNum(List<String> ids);

	/**
	 * 批量删除算法
	 *
	 * @param ids
	 * @return
	 */
	Boolean delete(List<String> ids);

	/**
	 * 更新算法
	 *
	 * @param resourceId 资源id
	 */
	void updateStateById(String resourceId);

	/**
	 * 通过类型查询算法
	 *
	 * @param type
	 * @return
	 */
	List<EvalAlgorithm> selectByType(String type);

	/**
	 * 分页查询算法列表
	 *
	 * @param listQuery
	 * @return
	 */
	IPage<EvalAlgorithm> page(EvalListQuery listQuery);

	/**
	 * 查询已使用的算法列表
	 *
	 * @param algorithmIds
	 * @return
	 */
	List<String> selectUsedAlgorithmIds(List<String> algorithmIds);

	/**
	 * 更新已使用的算法状态为隐藏
	 *
	 * @param selectUsedAlgorithmIds 查询使用算法id列表
	 */
	void updateStateByIds(List<String> selectUsedAlgorithmIds);

	/**
	 * run 算法
	 *
	 * @param algorithmId 算法id
	 * @param data        数据
	 * @param listData    列出数据
	 * @param otherArgs   其他参数
	 * @return {@code Object }
	 */
	Object runAlgorithm(String algorithmId, Object data, Object listData, Object otherArgs);
}

