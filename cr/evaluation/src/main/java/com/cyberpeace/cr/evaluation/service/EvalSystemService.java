package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估系统Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSystemService` 的接口，该接口继承自 `IService<EvalSystem>`。这个接口提供了与评估系统（`EvalSystem`）相关的服务方法。下面是对这段代码的详细分析： 
 * ### 接口定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.service` 
 * - **接口名称**：`EvalSystemService` 
 * - **继承关系**：`EvalSystemService` 继承自 `IService<EvalSystem>`。 
 * ### 方法定义 
 * 1. **批量删除评估体系** 
 * - **方法签名**：`Boolean batchDelete(List<String> ids)` 
 * - **描述**：此方法用于批量删除指定ID列表中的评估体系。 
 * - **参数**：`ids` 是一个包含多个字符串（评估体系ID）的列表。 
 * - **返回值**：`Boolean` 类型，表示操作是否成功。 
 * 2. **分页查询评估体系** 
 * - **方法签名**：`IPage<EvalSystem> page(EvalListQuery listQuery)` 
 * - **描述**：此方法用于分页查询评估体系。 
 * - **参数**：`listQuery` 是一个 `EvalListQuery` 对象，用于指定查询条件和分页信息。 
 * - **返回值**：`IPage<EvalSystem>` 类型，表示分页结果。 
 * ### 注释 
 * - **接口描述**：在接口上方有一个注释，说明了这是评估系统的服务接口，并且指出了作者和日期。 
 * - **方法注释**：每个方法都有详细的注释，描述了方法的功能、参数和返回值。 
 * ### 总结 
 * - **功能**：`EvalSystemService` 接口定义了与评估系统相关的服务方法，包括批量删除和分页查询。 
 * - **依赖**：接口使用了 `IService<EvalSystem>` 接口，并引入了 `EvalSystem` 和 `EvalListQuery` 类。 
 * - **可扩展性**：通过继承 `IService`，该接口可以利用 MyBatis-Plus 提供的一些默认实现，同时还可以自定义其他方法来满足特定需求。 
 * 这段代码是典型的业务服务接口定义，适用于需要管理评估系统的场景。 
**/
public interface EvalSystemService extends IService<EvalSystem> {

	/**
	 * 批量删除评估体系
	 *
	 * @param ids
	 * @return
	 */
	Boolean batchDelete(List<String> ids);

	/**
	 * 分页查询评估体系
	 *
	 * @param listQuery
	 * @return
	 */
	IPage<EvalSystem> page(EvalListQuery listQuery);
}

