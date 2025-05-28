/**
 * 这段代码定义了一个名为 `EvalIndicatorAdminController` 的控制器类，用于处理与评估指标相关的HTTP请求。以下是对其主要功能和结构的简要分析： 
 * ### 类及注解 
 * - **类名**：`EvalIndicatorAdminController` 
 * - **注解**： 
 * - `@RestController`：声明这是一个RESTful风格的控制器。 
 * - `@Slf4j`：引入日志记录功能。 
 * - `@RequestMapping("/cr/eval/indicator")`：定义了该控制器的基路径。 
 * ### 成员变量 
 * - 实例化了多个服务对象（如 `EvalIndicatorService`, `EvalIndicatorMapper` 等），以及远程服务对象 `RemotePythonService` 和事务模板 `TransactionTemplate`。 
 * ### 主要方法 
 * #### 1. `addEvalIndicator` 
 * - **功能**：添加新的评估指标。 
 * - **参数**：`EvalIndicatorReq` 请求体。 
 * - **操作**：在事务中执行指标的保存、聚合项和采集项的保存。 
 * - **返回值**：`BaseRsp<String>`，表示操作结果。 
 * #### 2. `checkMatrixDataConsistency` 
 * - **功能**：检查矩阵数据的一致性。 
 * - **参数**：`EvalAhpAlgorithmReq` 请求体。 
 * - **操作**：通过远程服务调用进行一致性校验。 
 * - **返回值**：`BaseRsp<String>`，包含校验结果。 
 * #### 3. `validateReq` 
 * - **功能**：验证请求体的有效性。 
 * - **参数**：`EvalIndicatorReq` 请求体。 
 * - **操作**：校验名称长度、名称重复性和描述长度。 
 * - **内部逻辑**：暂时未实现的层次分析法矩阵数据校验逻辑。 
 * #### 4. `editEvalIndicator` 
 * - **功能**：编辑现有的评估指标。 
 * - **参数**：`EvalIndicatorReq` 请求体。 
 * - **操作**：在事务中更新指标及其相关联的聚合项和采集项。 
 * - **返回值**：`BaseRsp<String>`，表示操作结果。 
 * #### 5. `getEvalIndex` 
 * - **功能**：获取单个评估指标。 
 * - **参数**：资源ID。 
 * - **操作**：从数据库中获取指定ID的评估指标并转换为响应对象。 
 * - **返回值**：`BaseRsp<EvalIndicatorRsp>`，包含评估指标信息。 
 * #### 6. `convertEvalIndicatorToEvalSystemRsp` 
 * - **功能**：将评估指标对象转换为响应对象。 
 * - **参数**：`EvalIndicator` 对象。 
 * - **操作**：复制属性并将相关集合转换为字符串数组。 
 * - **返回值**：`EvalIndicatorRsp` 响应对象。 
 * #### 7. `list` 
 * - **功能**：查询评估指标列表。 
 * - **参数**：`EvalNameListReq` 请求体。 
 * - **操作**：根据条件分页查询评估指标，并将结果转换为响应对象。 
 * - **返回值**：`BaseRsp<ListRsp<EvalIndicatorRsp>>`，包含分页后的评估指标列表。 
 * #### 8. `batchDelete` 
 * - **功能**：批量删除评估指标。 
 * - **参数**：`BatchDeleteReq` 删除请求体。 
 * - **操作**：检查指标是否被使用，如果未被使用则执行删除操作。 
 * - **返回值**：`BaseRsp<Boolean>`，表示删除操作的结果。 
 * ### 总结 
 * 该控制器提供了完整的评估指标管理功能，包括添加、编辑、查询和删除等操作。每个操作都涉及复杂的业务逻辑处理，如事务管理和远程服务调用。整体设计遵循了RESTful架构风格，并通过Spring框架的依赖注入机制实现了各组件之间的松耦合。 
**/
package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalAhpAlgorithmReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndicatorReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalNameListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalIndicatorRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndicatorMapper;
import com.cyberpeace.cr.evaluation.service.EvalAggregationService;
import com.cyberpeace.cr.evaluation.service.EvalCollectionService;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.service.RemotePythonService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评估指示器管理Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "后台-测试靶场-指标项管理")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/indicator")
@Slf4j
public class EvalIndicatorAdminController {

	/**
	 * 评估指示器Service
	 */
	@Autowired
	private EvalIndicatorService evalIndicatorService;

	/**
	 * 评估指示器Mapper
	 */
	@Autowired
	private EvalIndicatorMapper evalIndicatorMapper;

	/**
	 * 远程蟒Service
	 */
	@Autowired
	private RemotePythonService remotePythonService;

	/**
	 * 评估算法Mapper
	 */
	@Autowired
	private EvalAlgorithmMapper evalAlgorithmMapper;

	/**
	 * 评估收集Service
	 */
	@Autowired
	private EvalCollectionService evalCollectionService;

	/**
	 * 评估集合体Service
	 */
	@Autowired
	private EvalAggregationService evalAggregationService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 新增评估指示器
	 *
	 * @param evalIndicatorReq 评估指示器请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("新增指标项")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR)
	public BaseRsp<String>
			addEvalIndicator(@RequestBody @Validated EvalIndicatorReq evalIndicatorReq) {

		validateReq(evalIndicatorReq);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				// 获取所有的采集项列表
				List<String> collectionIds = StreamApiUtils.map(
						evalIndicatorReq.getEvalCollections(),
						evalCollectionContext -> evalCollectionContext.getEvalCollectionId());
				// 获取所有的采集项列表，保存指标项
				evalIndicatorService.saveBatchContext(evalIndicatorReq,
						UserInfoContext.getUserContext().getUserId(), collectionIds);
				// 保存聚合项和采集项
				evalAggregationService.saveBatchContext(evalIndicatorReq.getEvalAggregations());
				evalCollectionService.saveBatchContext(evalIndicatorReq.getEvalCollections());

			}
		});

		return new BaseRsp(evalIndicatorReq);
	}

	/**
	 * 检查矩阵数据一致性
	 *
	 * @param evalAhpAlgorithmReq 评估AHP算法请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("校验矩阵数据一致性")
	@PostMapping("/check_consistency")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR)
	public BaseRsp<String> checkMatrixDataConsistency(@RequestBody @Validated EvalAhpAlgorithmReq evalAhpAlgorithmReq) {
		// TODO 待填入
		List<String> ahpMatrixDataOrder = CrCommonValidator.validateAhpMatrixDataOrder(evalAhpAlgorithmReq.getMatrixData());
		FormatData data = new FormatData();
		data.setData(ahpMatrixDataOrder);
		List<String> res = remotePythonService.formatPerform(CrCommonConstants.AHP_CONSISTENCY_ID, null, data, null);
		return new BaseRsp(res);
	}


	/**
	 * 验证请求体
	 *
	 * @param req 请求体
	 */
	private void validateReq(EvalIndicatorReq req) {
		String resourceId = req.getEvalIndicatorId();

		// 校验名称长度
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalIndicatorMapper.existByName(req.getName(), resourceId)) {
			log.error("Name duplicate! name = [{}], resourceId = [{}]", req.getName(), resourceId);
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述长度
		CrCommonValidator.validateLength(req.getDescription(), CrCommonConstants.ZERO_STR_LEN,
				CrCommonConstants.MAX_DESC_LEN, CrBusinessErrorCode.ILLEGAL_DESC_LEN);

		// 层次分析法时 ahp参数格式矩阵数据校验
//		List<EvalIndicatorContext> evalIndicators = req.getEvalIndicators();
//		LambdaQueryWrapper<EvalAlgorithm> queryWrapper =  new LambdaQueryWrapper<>();
//		queryWrapper.eq(EvalAlgorithm::getMode, EvalAlgorithm.Mode.FORMAT_ALGORITHM.getCode());
//		List<EvalAlgorithm> ahpAlgorithms = evalAlgorithmMapper.selectList(queryWrapper);
//			List<String> ahpids = StreamApiUtils.getColumn(ahpAlgorithms, EvalAlgorithm::getResourceId);
//			if(!evalIndicators.isEmpty()){
//			EvalIndicatorContext evalIndicatorContext = evalIndicators.get(0);
//			String aggregatingAlgorithmId = evalIndicatorContext.getAggregatingAlgorithmId();
//			String matrixData = evalIndicatorContext.getMatrixData();
//			if(ahpids.contains(aggregatingAlgorithmId)){
//				List<String> matrixDataOrder = CrCommonValidator.validateAhpMatrixDataOrder(matrixData);
//				// TODO 一致性校验
//			}
//		}
//		List<EvalAggregationContext> evalAggregations = req.getEvalAggregations();
//		for(EvalAggregationContext evalAggregation:evalAggregations){
//			String aggregatingAlgorithmId = evalAggregation.getAggregatingAlgorithmId();
//			String matrixData = evalAggregation.getMatrixData();
//			if(ahpids.contains(aggregatingAlgorithmId)) {
//				List<String> matrixDataOrder = CrCommonValidator.validateAhpMatrixDataOrder(matrixData);
//				// TODO 一致性校验
//			}
//		}
	}

	/**
	 * 编辑 eval 指示符
	 *
	 * @param evalIndicatorReq 评估指示器请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("编辑指标项")
	@PutMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR)
	public BaseRsp<String>
			editEvalIndicator(@RequestBody @Validated EvalIndicatorReq evalIndicatorReq) {

		validateReq(evalIndicatorReq);

		EvalIndicator evalIndicator = evalIndicatorService
				.getById(evalIndicatorReq.getEvalIndicatorId());
		if (null == evalIndicator) {
			log.info("EvalIndicator not found! evalIndicatorId=[{}]",
					evalIndicatorReq.getEvalIndicatorId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				evalIndicatorService.deleteAggregationAndCollection(
						Lists.newArrayList(evalIndicatorReq.getEvalIndicatorId()));

				// 获取所有的采集项列表
				List<String> collectionIds = StreamApiUtils.map(
						evalIndicatorReq.getEvalCollections(),
						evalCollectionContext -> evalCollectionContext.getEvalCollectionId());

				evalIndicatorService.updateBatchContext(evalIndicatorReq,
						evalIndicatorReq.getEvalIndicators(),
						UserInfoContext.getUserContext().getUserId(), collectionIds);
				evalAggregationService.saveBatchContext(evalIndicatorReq.getEvalAggregations());
				evalCollectionService.saveBatchContext(evalIndicatorReq.getEvalCollections());
			}
		});

		return new BaseRsp(evalIndicatorReq);
	}

	/**
	 * 获取评估指数
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<EvalIndicatorRsp> }
	 */
	@ApiOperation("查询指标项")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR)
	public BaseRsp<EvalIndicatorRsp> getEvalIndex(
			@ApiParam(name = "resource_id", value = "模板id", required = true) String resource_id) {

		EvalIndicator evalIndicator = evalIndicatorService.getById(resource_id);
		if (null == evalIndicator) {
			log.info("EvalIndicator not found! evalIndicatorId=[{}]", resource_id);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		EvalIndicatorRsp evalIndicatorRsp = convertEvalIndicatorToEvalSystemRsp(evalIndicator);

		return new BaseRsp(evalIndicatorRsp);
	}

	/**
	 * 转换评估指示器自评估系统响应
	 *
	 * @param evalIndicator 评估指标
	 * @return {@code EvalIndicatorRsp }
	 */
	protected EvalIndicatorRsp convertEvalIndicatorToEvalSystemRsp(EvalIndicator evalIndicator) {
		EvalIndicatorRsp evalSystemRsp = BeanUtils.copy(evalIndicator, EvalIndicatorRsp.class);

		EvalIndicatorContext evalIndicatorContext = BeanUtils.copy(evalIndicator,
				EvalIndicatorContext.class);
		evalIndicatorContext.setAggregationIds(
				JsonUtil.toJsonObjectArray(evalIndicator.getAggregationIds(), String.class));
		evalIndicatorContext.setEvalIndicatorId(evalIndicator.getResourceId());

		evalSystemRsp.setEvalIndicatorId(evalIndicator.getResourceId());
		evalSystemRsp.setEvalCollections(
				evalCollectionService.getContextByEvalIndicatorId(evalIndicator.getResourceId()));
		evalSystemRsp.setEvalAggregations(
				evalAggregationService.getContextByEvalIndicatorId(evalIndicator.getResourceId()));;
		evalSystemRsp.setEvalIndicators(Lists.newArrayList(evalIndicatorContext));

		return evalSystemRsp;
	}

	/**
	 * 列表
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<EvalIndicatorRsp>> }
	 */
	@ApiOperation("列表查询")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM,
			MenuConstant.CMS_CR_EVALUATION_INDICATOR }, logical = Logical.OR)
	public BaseRsp<ListRsp<EvalIndicatorRsp>> list(EvalNameListReq listReq) {
		EvalListQuery listQuery = BeanUtils.copy(listReq, EvalListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		listQuery.setName(listReq.getPattern());

		// 默认时间排序
		listQuery.setSort("-create_time");

		IPage<EvalIndicator> data = evalIndicatorService.page(listQuery);
		data.convert(evalIndicator -> {
			EvalIndicatorRsp evalAlgorithmRsp = BeanUtils.copy(evalIndicator,
					EvalIndicatorRsp.class);
			String desc = EnumUtils.getEnumByCode(evalIndicator.getAttribute(), EvalIndexRef.DataAttr.class).getDesc();
			evalAlgorithmRsp.setAttributeVal(desc);
			evalAlgorithmRsp.setEvalCollectionNum(JsonUtil
					.toJsonObjectArray(evalIndicator.getCollectionIds(), String.class).size());
			evalAlgorithmRsp.setEvalAggregationNum(JsonUtil
					.toJsonObjectArray(evalIndicator.getAggregationIds(), String.class).size());

			evalAlgorithmRsp.setEvalIndicatorId(evalIndicator.getResourceId());

			return evalAlgorithmRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@DeleteMapping()
	@ApiOperation("批量删除")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR)
	public BaseRsp<Boolean> batchDelete(@RequestBody @Validated BatchDeleteReq deleteReq) {

		Integer useNum = evalIndicatorService.selectIndexUsedNum(deleteReq.getIds());

		if (useNum > 0) {
			log.info("Indicator Already Used! indicatorIds=[{}]",
					JsonUtil.arrayToString(deleteReq.getIds()));
			throw new BusinessException(CrBusinessErrorCode.INDICATOR_ALREADY_USED);
		}
		Boolean result = evalIndicatorService.batchDelete(deleteReq.getIds());

		return new BaseRsp<>(result);

	}
}