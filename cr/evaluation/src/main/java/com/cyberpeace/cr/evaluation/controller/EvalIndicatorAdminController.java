package com.cyberpeace.cr.evaluation.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.annoations.PermissionOperation;
import com.cyberpeace.common.constant.TrialButtonConstant;
import com.cyberpeace.common.model.user.UserInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

@Api(tags = "后台-测试靶场-指标项管理")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/indicator")
@Slf4j
public class EvalIndicatorAdminController {

	@Autowired
	private EvalIndicatorService evalIndicatorService;

	@Autowired
	private EvalIndicatorMapper evalIndicatorMapper;

	@Autowired
	private RemotePythonService remotePythonService;

	@Autowired
	private EvalAlgorithmMapper evalAlgorithmMapper;

	@Autowired
	private EvalCollectionService evalCollectionService;

	@Autowired
	private EvalAggregationService evalAggregationService;

	@Autowired
	private TransactionTemplate transactionTemplate;

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

	@ApiOperation("编辑指标项")
	@PutMapping("")
	@PermissionOperation(TrialButtonConstant.CMS_CR_EVALUATION_INDICATOR)
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR)
	public BaseRsp<String>
			editEvalIndicator(@RequestBody @Validated EvalIndicatorReq evalIndicatorReq) {
		// 试用账号编辑改动
		EvalIndicator evalIndicator = evalIndicatorService
				.getById(evalIndicatorReq.getEvalIndicatorId());
		if(null == evalIndicator){
			log.info("EvalIndicator not found! evalIndicatorId=[{}]",
					evalIndicatorReq.getEvalIndicatorId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		String createUserId = evalIndicator.getCreateUserId();
		UserInfo userContext = UserInfoContext.getUserContext();
		if(!createUserId.equals(userContext.getUserId())){
			throw new BusinessException(ErrorCode.TRAIL_UNAUTHORIZED);
		}
		validateReq(evalIndicatorReq);


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

	private EvalIndicatorRsp convertEvalIndicatorToEvalSystemRsp(EvalIndicator evalIndicator) {
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

	@DeleteMapping()
	@ApiOperation("批量删除")
	@PermissionOperation(TrialButtonConstant.CMS_CR_EVALUATION_INDICATOR)
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR)
	public BaseRsp<Boolean> batchDelete(@RequestBody @Validated BatchDeleteReq deleteReq) {


		// 试用账号删除非自己账号创建的算法抛异常
		LambdaQueryWrapper<EvalIndicator> query =  new LambdaQueryWrapper<>();
		UserInfo userContext = UserInfoContext.getUserContext();
		query.ne(EvalIndicator::getCreateUserId, userContext.getUserId());
		query.in(EvalIndicator::getResourceId, deleteReq.getIds());
		List<EvalIndicator> evalIndicators = evalIndicatorMapper.selectList(query);
		if (evalIndicators.size() > 0){
			throw new BusinessException(ErrorCode.TRAIL_UNAUTHORIZED);
		}

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