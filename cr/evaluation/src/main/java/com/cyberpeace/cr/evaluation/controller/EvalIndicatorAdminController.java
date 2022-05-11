package com.cyberpeace.cr.evaluation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndicatorReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalNameListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalIndicatorRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.service.EvalAggregationService;
import com.cyberpeace.cr.evaluation.service.EvalCollectionService;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
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
	private EvalIndicatorService evalIndexService;

	@Autowired
	private EvalIndicatorService evalIndicatorService;

	@Autowired
	private EvalCollectionService evalCollectionService;

	@Autowired
	private EvalAggregationService evalAggregationService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation("新增指标项")
	@PostMapping("")
	public BaseRsp<String>
			addEvalIndicator(@RequestBody @Validated EvalIndicatorReq evalIndicatorReq) {

		String connlist = evalIndicatorReq.getConnlist();

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
		return new BaseRsp(connlist);
	}

	@ApiOperation("编辑体系指标")
	@PutMapping("")
	public BaseRsp<String>
			editEvalIndicator(@RequestBody @Validated EvalIndicatorReq evalIndicatorReq) {

		String connlist = evalIndicatorReq.getConnlist();

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

		return new BaseRsp(connlist);
	}

	@ApiOperation("查询体系指标")
	@GetMapping("")
	public BaseRsp<EvalSystemRsp> getEvalIndex(
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
	public BaseRsp<ListRsp<EvalIndicator>> list(EvalNameListReq listReq) {
		EvalListQuery listQuery = BeanUtils.copy(listReq, EvalListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		// 默认时间排序
		listQuery.setSort("-create_time");

		IPage<EvalIndicator> data = evalIndicatorService.page(listQuery);
		data.convert(evalIndicator -> {
			EvalIndicatorRsp evalAlgorithmRsp = BeanUtils.copy(evalIndicator,
					EvalIndicatorRsp.class);
			evalAlgorithmRsp.setEvalCollectionNum(JsonUtil
					.toJsonObjectArray(evalIndicator.getCollectionIds(), String.class).size());
			evalAlgorithmRsp.setEvalAggregationNum(JsonUtil
					.toJsonObjectArray(evalIndicator.getAggregationIds(), String.class).size());

			evalAlgorithmRsp.setEvalIndicatorId(evalIndicator.getResourceId());

			return evalAlgorithmRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	/*
	
		
	
	
		private List<EvalIndex> convertIndexReqToIndex(List<IndexContext> firstIndexes, Integer level) {
	
			List<EvalIndex> evalIndexList = firstIndexes.stream().map(indexReq -> {
				EvalIndex evalIndex = BeanUtils.copy(indexReq, EvalIndex.class);
	
				evalIndex.setResourceId(indexReq.getIndexId());
				evalIndex.setChildrenIds(JsonUtil.arrayToString(indexReq.getChildrenIds()));
				evalIndex.setAggregatingAlgorithmId(indexReq.getAlgorithmId());
				evalIndex.setAggregatingAlgorithmParams(indexReq.getParams());
				evalIndex.setLevel(level);
	
				return evalIndex;
			}).collect(Collectors.toList());
	
			return evalIndexList;
		}
	
		*/
	@DeleteMapping()
	@ApiOperation("批量删除")
	public BaseRsp<Boolean> batchDelete(@RequestBody @Validated BatchDeleteReq deleteReq) {

		Boolean result = evalIndicatorService.batchDelete(deleteReq.getIds());

		return new BaseRsp<>(result);
	}
}