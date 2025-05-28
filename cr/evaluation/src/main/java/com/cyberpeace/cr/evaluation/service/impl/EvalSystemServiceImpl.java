/**
 * 这段代码定义了一个名为 `EvalSystemServiceImpl` 的类，它是 `EvalSystemService` 接口的一个实现。这个类主要用于处理与评估系统（EvalSystem）相关的业务逻辑，包括批量删除和分页查询。以下是详细的分析： 
 * ### 类定义和注解 
 * - **类名**：`EvalSystemServiceImpl` 
 * - **继承**：`ServiceImpl<EvalSystemMapper, EvalSystem>`，这是 MyBatis-Plus 提供的基类，用于简化 CRUD 操作。 
 * - **注解**： 
 * - `@Service`：表明这是一个 Spring 管理的服务层组件。 
 * ### 成员变量 
 * - **事务模板**：`private TransactionTemplate transactionTemplate;` 
 * - **评估指数映射器**：`private EvalIndexMapper evalIndexMapper;` 
 * - **评估系统映射器**：`private EvalSystemMapper evalSystemMapper;` 
 * 这些成员变量通过 `@Autowired` 注入，用于操作数据库。 
 * ### 方法 
 * #### `batchDelete` 
 * - **功能**：批量删除评估系统及其关联的评估指数。 
 * - **参数**：`List<String> evalSystemIds`，包含需要删除的评估系统的 ID 列表。 
 * - **返回值**：`Boolean`，表示操作是否成功。 
 * - **实现**： 
 * - 使用 `TransactionTemplate` 开启一个事务。 
 * - 调用 `evalIndexMapper.selectIdsByEvalSystemIds` 获取与这些评估系统关联的评估指数 ID 列表。 
 * - 删除评估系统及其关联的评估指数。 
 * - 返回 `true` 表示操作成功。 
 * #### `page` 
 * - **功能**：分页查询评估系统。 
 * - **参数**：`EvalListQuery listQuery`，包含查询条件和分页信息。 
 * - **返回值**：`IPage<EvalSystem>`，分页后的评估系统列表。 
 * - **实现**： 
 * - 将查询条件转换为 `Page` 对象。 
 * - 调用 `evalSystemMapper.listEvalSystems` 进行分页查询。 
 * ### 总结 
 * - `EvalSystemServiceImpl` 是一个实现了 `EvalSystemService` 接口的服务类，主要负责处理评估系统的业务逻辑。 
 * - 该类使用了 MyBatis-Plus 的 `ServiceImpl` 基类来简化数据访问。 
 * - 类中的方法通过事务管理确保数据的一致性，并且能够处理复杂的业务逻辑，如批量删除和分页查询。 
**/
package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalSystemMapper;
import com.cyberpeace.cr.evaluation.service.EvalSystemService;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估系统Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class EvalSystemServiceImpl extends ServiceImpl<EvalSystemMapper, EvalSystem> implements EvalSystemService {

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 评估指数Mapper
	 */
	@Autowired
	private EvalIndexMapper evalIndexMapper;

	/**
	 * 评估系统Mapper
	 */
	@Autowired
	private EvalSystemMapper evalSystemMapper;

	/**
	 * 批量删除
	 *
	 * @param evalSystemIds 评估系统id列表
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean batchDelete(List<String> evalSystemIds) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<String> indexIds = evalIndexMapper.selectIdsByEvalSystemIds(evalSystemIds);

				evalSystemMapper.deleteBatchIds(evalSystemIds);
				if (indexIds.size() > 0) {
					evalIndexMapper.deleteBatchIds(indexIds);
				}

			}
		});

		return true;
	}

	/**
	 * 分页
	 *
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalSystem> }
	 */
	@Override
	public IPage<EvalSystem> page(EvalListQuery listQuery) {

		Page page = listQuery.toPageParam();

        return evalSystemMapper.listEvalSystems(page, listQuery);
	}
}
