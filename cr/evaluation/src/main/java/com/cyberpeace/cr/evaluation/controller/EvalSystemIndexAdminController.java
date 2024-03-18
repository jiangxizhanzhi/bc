package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalIndicatorRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemAllRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "后台-测试靶场-指标体系")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/system")
@Slf4j
public class EvalSystemIndexAdminController {

	@Autowired
	private EvalIndexService evalIndexService;

	@Autowired
	private EvalSystemMapper evalSystemMapper;

	@Autowired
	private EvalSystemService evalSystemService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalIndexMapper evalIndexMapper;

	@Autowired
	private EvalIndicatorMapper evalIndicatorMapper;

	@Autowired
	private EvalIndicatorService evalIndicatorService;

	@Autowired
	private EvalAggregationMapper evalAggregationMapper;

	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	@Autowired
	private EvalAggregationService evalAggregationService;

	@Autowired
	private EvalCollectionService evalCollectionService;

	@ApiOperation("新增体系指标")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemReq> addEvalIndex(@RequestBody @Validated EvalSystemReq evalSystemReq) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				EvalSystem evalSystem = saveEvalIndex(evalSystemReq);
				evalSystem.setCreateUserId(UserInfoContext.getUserContext().getUserId());
				evalSystemService.save(evalSystem);
			}
		});

		return new BaseRsp<>(evalSystemReq);
	}

	@ApiOperation("编辑体系指标")
	@PutMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemReq> editEvalIndex(@RequestBody @Validated EvalSystemReq evalSystemReq) {

		EvalSystem evalSystem = evalSystemService.getById(evalSystemReq.getEvalSystemId());
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", evalSystemReq.getEvalSystemId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				evalIndexService.deleteBatchByEvalSystemId(evalSystemReq.getEvalSystemId());

				EvalSystem evalSystem = saveEvalIndex(evalSystemReq);

				evalSystemService.updateById(evalSystem);
			}
		});

		return new BaseRsp<>(evalSystemReq);
	}

	private EvalSystem saveEvalIndex(@Validated @RequestBody EvalSystemReq evalSystemReq) {
		validateReq(evalSystemReq);

		evalIndexService.saveBatch(convertIndexReqToIndex(evalSystemReq.getFirstIndexes(),
				EvalIndex.Level.FIRST.getCode()));

		// 保存新的三级指标，并与指标项关联
		List<EvalIndex> thirdIndexList = convertIndexReqToIndex(evalSystemReq.getThirdIndexes(),
				EvalIndex.Level.THIRD.getCode());
		evalIndexService.saveBatch(thirdIndexList);

		// 修改二级指标与新三级指标的关联
		changeChildrenIds(evalSystemReq.getSecondIndexes(), thirdIndexList);

		List<EvalIndex> secondIndexList = convertIndexReqToIndex(evalSystemReq.getSecondIndexes(),
				EvalIndex.Level.SECOND.getCode());
		evalIndexService.saveBatch(secondIndexList);

		EvalSystem evalSystem = BeanUtils.copy(evalSystemReq, EvalSystem.class);
		evalSystem.setResourceId(evalSystemReq.getEvalSystemId());
		// 修改连接线关联
		evalSystem.setConnlist(changeConnlist(evalSystemReq.getConnlist(), thirdIndexList));

		evalSystem.setThirdIndexNum(thirdIndexList.size());
		evalSystem.setSecondIndexNum(secondIndexList.size());
		evalSystem.setFirstIndexNum(1);

		return evalSystem;
	}

	private String changeConnlist(String connlist, List<EvalIndex> thirdIndexList) {
		for (EvalIndex evalIndex : thirdIndexList) {
			connlist = connlist.replace(evalIndex.getEvalIndicatorId(), evalIndex.getResourceId());
		}
		return connlist;
	}

	private void validateReq(EvalSystemReq req) {
		String resourceId = req.getEvalSystemId();

		// 校验名称长度
		CrCommonValidator.validateNameLen(req.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);

		// 校验名称重复
		if (evalSystemMapper.existByName(req.getName(), resourceId)) {
			log.error("Name duplicate! name = [{}] , resourceId = [{}]", req.getName(), resourceId);
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		// 校验描述长度
		CrCommonValidator.validateLength(req.getDescription(), CrCommonConstants.ZERO_STR_LEN,
				CrCommonConstants.MAX_DESC_LEN, CrBusinessErrorCode.ILLEGAL_DESC_LEN);
	}

	private void changeChildrenIds(List<IndexContext> secondIndexList,
			List<EvalIndex> thirdIndexList) {
		Map<String, String> thirdIndexMaps = StreamApiUtils.toMap(thirdIndexList,
				EvalIndex::getEvalIndicatorId, EvalIndex::getResourceId);

		for (IndexContext indexContext : secondIndexList) {
			indexContext.setChildrenIds(indexContext.getChildrenIds().stream()
					.map(thirdIndexMaps::get).collect(Collectors.toList()));
		}
	}

	@ApiOperation("查询体系指标")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemRsp> getEvalIndex(
			@ApiParam(name = "resource_id", value = "模板id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {

		EvalSystem evalSystem = evalSystemService.getById(resourceId);
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		EvalSystemRsp evalSystemRsp = evalSystemService.convertEvalSystemToEvalSystemRsp(evalSystem);
		return new BaseRsp<>(evalSystemRsp);
	}

	private List<EvalIndex> convertIndexReqToIndex(List<IndexContext> indexContextList,
			Integer level) {

		return indexContextList.stream().map(indexReq -> {
			EvalIndex evalIndex = BeanUtils.copy(indexReq, EvalIndex.class);

			evalIndex.setResourceId(indexReq.getIndexId());
			evalIndex.setChildrenIds(JsonUtil.arrayToString(indexReq.getChildrenIds()));
			evalIndex.setAggregatingAlgorithmId(indexReq.getAlgorithmId());
			evalIndex.setAggregatingAlgorithmParams(indexReq.getParams());
			evalIndex.setLevel(level);

			// 三级指标特殊，前台传过来的是指标项，这里新建一个，并建立关联关系,修改连接线和子指标列表
			if (EvalIndex.Level.THIRD.getCode().equals(level)) {

				// 三级指标的id等于指标项id，说明是新增需要修改id，否则是编辑不改id
				if (indexReq.getIndexId().equals(indexReq.getEvalIndicatorId())) {
					evalIndex.setResourceId(null);
					evalIndex.setEvalIndicatorId(indexReq.getIndexId());

				}
				evalIndex.setEvalIndicatorId(indexReq.getEvalIndicatorId());

			}

			return evalIndex;
		}).collect(Collectors.toList());
	}

	@DeleteMapping()
	@ApiOperation("批量删除")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<Boolean> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {

		Boolean result = evalSystemService.batchDelete(deleteReq.getIds());

		return new BaseRsp<>(result);
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	@RequiresPermissions(value = { MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM,
			MenuConstant.WEB_CR_EVALUATION }, logical = Logical.OR)
	public BaseRsp<ListRsp<EvalSystemRsp>> list(EvalNameListReq listReq) {
		EvalListQuery listQuery = BeanUtils.copy(listReq, EvalListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		listQuery.setName(listReq.getPattern());

		// 默认时间排序
		listQuery.setSort("-create_time");

		IPage<EvalSystem> data = evalSystemService.page(listQuery);

		return new BaseRsp<>(PageUtil.getListResult(data));
	}

	@ApiOperation("复制体系指标")
	@PostMapping("copy")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemReq> copyEvalIndex(@RequestBody @Validated EvalSystemCopyReq evalSystemReq) {
		// 复制当前已有体系 生成一条名称+【复制】的新体系数据
		// 复用新增指标体系得方法 用新指标项id替换旧得 替换后得参数模拟前端入参
		List<EvalSystem> evalSystems = evalSystemService.getBaseMapper().selectList(null);
		List<String> names = evalSystems.stream().map(EvalSystem::getName).collect(Collectors.toList());
		EvalSystem byId = evalSystemService.getById(evalSystemReq.getEvalSystemId());
		if (null == byId) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", evalSystemReq.getEvalSystemId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		EvalSystemRsp evalSystemRsp = evalSystemService.convertEvalSystemToEvalSystemRsp(byId);
		String indexId1 = evalSystemRsp.getFirstIndexes().get(0).getIndexId();
		List<IndexContext> secondIndexes = evalSystemRsp.getSecondIndexes();
		List<IndexContext> thirdIndexes = evalSystemRsp.getThirdIndexes();

		List<IndexContext> indexesByEvalSystemId = evalIndexService.getIndexesByEvalSystemId(evalSystemReq.getEvalSystemId(), EvalIndex.Level.THIRD.getCode());
		Map<String, String> thirdIndexMaps = StreamApiUtils.toMap(indexesByEvalSystemId,
				IndexContext::getIndexId, IndexContext::getEvalIndicatorId);

		for(IndexContext index:secondIndexes){
			List<String> childrenIds = index.getChildrenIds();
			List<String> replaceIds = new ArrayList<>();
			for(String child:childrenIds){
				String indicator = thirdIndexMaps.get(child);
				replaceIds.add(indicator);

			}
			index.setChildrenIds(replaceIds);
		}
		evalSystemRsp.setSecondIndexes(secondIndexes);

		String jsonString = JsonUtil.toJsonString(evalSystemRsp);
		String id = IDGeneratorUtil.getID();
		jsonString = jsonString.replaceAll(indexId1, id);
		for (IndexContext indexContext : thirdIndexes) {
			String indexId = indexContext.getIndexId();
			String currentId = IDGeneratorUtil.getID();
			jsonString = jsonString.replaceAll(indexId, currentId);

		}

		for (IndexContext indexContext : secondIndexes) {
			String indexId = indexContext.getIndexId();
			String currentId = IDGeneratorUtil.getID();
			jsonString = jsonString.replaceAll(indexId, currentId);

		}
		String name = evalSystemRsp.getName();
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		while (names.contains(sb.toString())){
			sb.append("（复制）");
		}

		jsonString = jsonString.replaceAll(name, sb.toString());
		EvalSystemReq jsonObject = JsonUtil.toJsonObject(jsonString, EvalSystemReq.class);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				EvalSystem evalSystem = saveEvalIndex(jsonObject);
				evalSystem.setCreateUserId(UserInfoContext.getUserContext().getUserId());
				evalSystemService.save(evalSystem);
			}
		});

		return new BaseRsp<>(jsonObject);
	}


	@ApiOperation("查询调试体系指标")
	@GetMapping("calculate")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemRsp> getEvalIndexByCalc(
			@ApiParam(name = "resource_id", value = "模板id", required = true) @RequestParam(
					value = "resource_id") String resourceId) {

		EvalSystem evalSystem = evalSystemService.getById(resourceId);
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		EvalSystemRsp evalSystemRsp = evalSystemService.convertEvalSystemToEvalSystemRsp(evalSystem, false);
		return new BaseRsp<>(evalSystemRsp);
	}

	@PostMapping("calculate")
	@ApiOperation("调试")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemRsp> calculate(@RequestBody @Validated EvalSystemCalReq evalSystemCalReq) {
		List<IndexContext> thirdIndexes = evalSystemCalReq.getThirdIndexes();
		// 调试之前清空之前的计算结果
		String evalSystemId = evalSystemCalReq.getEvalSystemId();
		EvalSystem byId = evalSystemService.getById(evalSystemId);
		if (null == byId) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", evalSystemId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		List<EvalIndex> indexList = evalIndexMapper.selectListBySystemIdAndLevel(evalSystemId, null);
		indexList = indexList.stream().filter(s-> s.getLevel() <= EvalIndex.Level.THIRD.getCode()).collect(Collectors.toList());
		indexList.forEach(index -> {
			index.setCalScore(null);
			index.setCalMessage(null);
		});
		evalIndexService.updateBatchById(indexList);

		EvalSystemRsp rsp = evalSystemService.calWithThirdIndex(thirdIndexes, evalSystemId, false);

		return new BaseRsp<>(rsp);
	}

	@PostMapping("test")
	@ApiOperation("试验")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemAllRsp> test(@RequestBody @Validated EvalSystemTestReq evalSystemTestReq) {
		String evalSystemId = evalSystemTestReq.getEvalSystemId();
		EvalSystem byId = evalSystemService.getById(evalSystemId);
		if (null == byId) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", evalSystemId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 试验之前清空之前的计算结果
		clearResultBeforeCurrentTest(byId);


		EvalSystemAllRsp result = evalSystemService.testWithStr(evalSystemTestReq);

		return new BaseRsp<>(result);
	}

	private void clearResultBeforeCurrentTest(EvalSystem byId) {

		List<EvalIndex> indexList = evalIndexMapper.selectListBySystemIdAndLevel(byId.getResourceId(), null);
		indexList = indexList.stream().filter(s-> s.getLevel() <= EvalIndex.Level.THIRD.getCode()).collect(Collectors.toList());
		List<String> indicatorIds = indexList.stream().filter(s -> EvalIndex.Level.THIRD.getCode().equals(s.getLevel())).map(EvalIndex::getEvalIndicatorId).collect(Collectors.toList());
		indexList.forEach(index -> {
			index.setTestScore(null);
			index.setTestMessage(null);
		});
		List<EvalAggregation> evalAggregations = evalAggregationMapper.selectIdsByEvalIndicatorIds(indicatorIds);
		evalAggregations.forEach(evalAggregation -> {
			evalAggregation.setScore(null);
			evalAggregation.setMessage(null);
		});

		List<EvalCollection> collects = evalCollectionMapper.selectByEvalIndicators(indicatorIds);
		collects.forEach(evalCollection -> {
			evalCollection.setProgress(EvalVariableRef.Progress.NOT_START.getCode());
			evalCollection.setScore(null);
			evalCollection.setMessage(null);
		});
		List<EvalIndex> finalIndexList = indexList;
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				evalIndexService.updateBatchById(finalIndexList);
				evalAggregationService.updateBatchById(evalAggregations);
				evalCollectionService.updateBatchById(collects);
			}
		});
	}

	@ApiOperation("查询体系指标All(全)")
	@GetMapping("all")
	@RequiresPermissions(MenuConstant.CMS_CR_EVALUATION_INDICATOR_SYSTEM)
	public BaseRsp<EvalSystemAllRsp> getEvalIndexAll(
			@ApiParam(name = "resource_id", value = "模板id", required = true)
			@RequestParam(value = "resource_id") String resourceId) {
		// 调试时 前端展示全体系数据
		EvalSystem evalSystem = evalSystemService.getById(resourceId);
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		EvalSystemRsp evalSystemRsp = evalSystemService.convertEvalSystemToEvalSystemRsp(evalSystem, true);
		EvalSystemAllRsp rsp = new EvalSystemAllRsp();
		BeanUtils.copyProperties(evalSystemRsp, rsp);
		List<EvalIndex> evalIndexList = evalIndexMapper.selectListBySystemIdAndLevel(resourceId,
				EvalIndex.Level.THIRD.getCode());

		List<String> column = StreamApiUtils.getColumn(evalIndexList, EvalIndex::getEvalIndicatorId);
		List<EvalIndicator> evalIndicators = evalIndicatorMapper.selectIndexes(column);
		List<EvalIndex> indexList = evalIndexMapper.selectListBySystemIdAndLevel(resourceId, EvalIndex.Level.THIRD.getCode());
		Map<String, String> collect = indexList.stream().collect(Collectors.toMap(EvalIndex::getEvalIndicatorId, EvalIndex::getResourceId));
		List<IndexContext> thirdIndexes = evalSystemRsp.getThirdIndexes();
		Map<String, IndexContext> contextMap = StreamApiUtils.toMap(thirdIndexes, IndexContext::getEvalIndicatorId, s -> s);
		List<IndexContext> thirdIndex = new ArrayList<>();
		evalIndicators.forEach(evalIndicator -> {
			EvalIndicatorRsp evalIndicatorRsp = evalIndicatorService.convertEvalIndicatorToEvalSystemRsp(evalIndicator);
			// 待前端确认返回展示格式  3级指标项 链接 聚合项和采集项时 使用的是 指标项id 非指标项resource_id
			String connlist = evalIndicatorRsp.getConnlist();
			connlist = connlist.replaceAll(evalIndicator.getResourceId(),collect.get(evalIndicator.getResourceId()));

			List<String> aggregationIds = JsonUtil
					.toJsonObjectArray(evalIndicator.getAggregationIds(), String.class);
			String evalIndicatorId = evalIndicator.getResourceId();
			IndexContext indexContext = contextMap.get(evalIndicatorId);
			indexContext.setChildrenIds(aggregationIds);
			thirdIndex.add(indexContext);
			String rspConnlist = rsp.getConnlist();
			rsp.setConnlist(rspConnlist + connlist);
			rsp.getFourthIndexes().addAll(evalIndicatorRsp.getEvalAggregations());
			rsp.getFifthIndexes().addAll(evalIndicatorRsp.getEvalCollections());

		});
		rsp.setThirdIndexes(thirdIndex);
		return new BaseRsp<>(rsp);
	}
}