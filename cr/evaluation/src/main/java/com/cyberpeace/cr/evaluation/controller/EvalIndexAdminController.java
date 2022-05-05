package com.cyberpeace.cr.evaluation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalSystemReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.service.EvalIndexService;
import com.cyberpeace.cr.evaluation.service.EvalSystemService;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "后台-测试靶场-体系指标")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/system")
@Slf4j
public class EvalIndexAdminController {

	@Autowired
	private EvalIndexService evalIndexService;

	@Autowired
	private EvalSystemService evalSystemService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation("新增体系指标")
	@PostMapping("")
	public BaseRsp<String> addEvalIndex(@RequestBody @Validated EvalSystemReq evalSystemReq) {

		String connlist = evalSystemReq.getConnlist();

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				evalIndexService.saveBatch(convertIndexReqToIndex(evalSystemReq.getFirstIndexes(),
						EvalIndex.Level.FIRST.getCode()));
				evalIndexService.saveBatch(convertIndexReqToIndex(evalSystemReq.getSecondIndexes(),
						EvalIndex.Level.SECONDE.getCode()));
				evalIndexService.saveBatch(convertIndexReqToIndex(evalSystemReq.getThirdIndexes(),
						EvalIndex.Level.THIRD.getCode()));

				EvalSystem evalSystem = BeanUtils.copy(evalSystemReq, EvalSystem.class);
				evalSystem.setResourceId(evalSystemReq.getEvalSystemId());
				evalSystem.setCreateUserId(UserInfoContext.getUserContext().getUserId());
				evalSystemService.save(evalSystem);
			}
		});

		return new BaseRsp(connlist);
	}

	@ApiOperation("编辑体系指标")
	@PutMapping("")
	public BaseRsp<String> editEvalIndex(@RequestBody @Validated EvalSystemReq evalSystemReq) {

		String connlist = evalSystemReq.getConnlist();

		EvalSystem evalSystem = evalSystemService.getById(evalSystemReq.getEvalSystemId());
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", evalSystemReq.getEvalSystemId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				evalIndexService.deleteBatchByEvalSystemId(evalSystemReq.getEvalSystemId());

				evalIndexService.saveBatch(convertIndexReqToIndex(evalSystemReq.getFirstIndexes(),
						EvalIndex.Level.FIRST.getCode()));
				evalIndexService.saveBatch(convertIndexReqToIndex(evalSystemReq.getSecondIndexes(),
						EvalIndex.Level.SECONDE.getCode()));
				evalIndexService.saveBatch(convertIndexReqToIndex(evalSystemReq.getThirdIndexes(),
						EvalIndex.Level.THIRD.getCode()));

				EvalSystem evalSystem = BeanUtils.copy(evalSystemReq, EvalSystem.class);
				evalSystem.setResourceId(evalSystemReq.getEvalSystemId());

				evalSystemService.updateById(evalSystem);
			}
		});

		return new BaseRsp(connlist);
	}

	@ApiOperation("查询体系指标")
	@GetMapping("")
	public BaseRsp<EvalSystemRsp> getEvalIndex(
			@ApiParam(name = "resource_id", value = "模板id", required = true) String resource_id) {

		EvalSystem evalSystem = evalSystemService.getById(resource_id);
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", resource_id);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		EvalSystemRsp evalSystemRsp = convertEvalSystemToEvalSystemRsp(evalSystem);
		return new BaseRsp(evalSystemRsp);
	}

	private EvalSystemRsp convertEvalSystemToEvalSystemRsp(EvalSystem evalSystem) {
		EvalSystemRsp evalSystemRsp = BeanUtils.copy(evalSystem, EvalSystemRsp.class);
		evalSystemRsp.setEvalSystemId(evalSystem.getResourceId());
		evalSystemRsp.setFirstIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.FIRST.getCode()));
		evalSystemRsp.setSecondIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.SECONDE.getCode()));
		evalSystemRsp.setThirdIndexes(evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.THIRD.getCode()));

		return evalSystemRsp;
	}

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

	@DeleteMapping()
	@ApiOperation("批量删除")
	public BaseRsp<Boolean> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		Boolean result = evalSystemService.batchDelete(deleteReq.getIds());

		return new BaseRsp<>(result);
	}

}