package com.cyberpeace.cr.evaluation.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSystemTreeRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.dao.entity.EvalSystem;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexService;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.EvalSystemService;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "前台-测试靶场-获取树形指标体系")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/system_tree")
@Slf4j
public class EvalSystemController {

	@Autowired
	private EvalIndexService evalIndexService;

	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	@Autowired
	private EvalIndicatorService evalIndicatorService;

	@Autowired
	private EvalSystemService evalSystemService;

	@ApiOperation("查询体系指标")
	@GetMapping("")
	public BaseRsp<EvalSystemTreeRsp> getEvalIndexTree(
			@ApiParam(name = "resource_id", value = "模板id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {

		EvalSystem evalSystem = evalSystemService.getById(resourceId);
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		EvalSystemTreeRsp evalSystemRsp = convertEvalSystemToEvalSystemTreeRsp(evalSystem);

		return new BaseRsp<>(evalSystemRsp);
	}

	private EvalSystemTreeRsp convertEvalSystemToEvalSystemTreeRsp(EvalSystem evalSystem) {
		EvalSystemRsp evalSystemRsp = BeanUtils.copy(evalSystem, EvalSystemRsp.class);

		evalSystemRsp.setEvalSystemId(evalSystem.getResourceId());

		List<IndexContext> firstIndexes = evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.FIRST.getCode());
		if (firstIndexes.size() > 1) {
			log.info("firstIndexes is more than 1! EvalSystemId=[{}]", evalSystem.getResourceId());
			throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
		}

		List<IndexContext> secondIndexes = evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.SECOND.getCode());

		List<IndexContext> thirdIndexes = evalIndexService.getIndexesByEvalSystemId(
				evalSystem.getResourceId(), EvalIndex.Level.THIRD.getCode());

		// 获取所有二级、三级指标的Map
		Map<String, EvalSystemTreeRsp> evalSystemTreeRspMap = getMapByIndex(secondIndexes,
				thirdIndexes);

		// List<EvalSystemTreeRsp>
		// 获取一级指标的结构
		EvalSystemTreeRsp evalSystemTreeRsp = new EvalSystemTreeRsp();
		evalSystemTreeRsp.setResourceId(firstIndexes.get(0).getIndexId());
		evalSystemTreeRsp.setLevel(EvalIndex.Level.FIRST.getCode());
		evalSystemTreeRsp.setName(firstIndexes.get(0).getName());

		List<EvalSystemTreeRsp> childrens = getChildrens(firstIndexes.get(0).getChildrenIds(),
				evalSystemTreeRspMap);
		evalSystemTreeRsp.setChildren(childrens);

		// 设置二级指标的子节点
		for (IndexContext secondIndex : secondIndexes) {
			List<EvalSystemTreeRsp> secondChildrens = getChildrens(secondIndex.getChildrenIds(),
					evalSystemTreeRspMap);
			evalSystemTreeRspMap.get(secondIndex.getIndexId()).setChildren(secondChildrens);
		}

		// 循环查询并设置三级指标的采集项
		for (IndexContext thirdIndex : thirdIndexes) {
			EvalIndicator evalIndicator = evalIndicatorService
					.getById(thirdIndex.getEvalIndicatorId());
			log.info("thirdIndex.index_id :{} evalIndicator is null :{}", thirdIndex.getIndexId(),evalIndicator==null);

			List<EvalCollection> evalCollections = evalCollectionMapper
					.selectBythirdIndexId(thirdIndex.getIndexId());
			List<EvalSystemTreeRsp> thirdChildrens = evalCollections.stream()
					.map(evalCollection -> {
						EvalSystemTreeRsp systemTreeRsp = new EvalSystemTreeRsp();
						systemTreeRsp.setName(evalCollection.getName());
						systemTreeRsp.setLevel(5);
						systemTreeRsp.setResourceId(evalCollection.getResourceId());

						return systemTreeRsp;
					}).collect(Collectors.toList());

			evalSystemTreeRspMap.get(thirdIndex.getIndexId()).setChildren(thirdChildrens);
			evalSystemTreeRspMap.get(thirdIndex.getIndexId())
					.setThirdLevelType(evalIndicator.getDatatype());

		}

		return evalSystemTreeRsp;
	}

	private List<EvalSystemTreeRsp> getChildrens(List<String> ids,
			Map<String, EvalSystemTreeRsp> evalSystemTreeRspMap) {
		List<EvalSystemTreeRsp> childrens = Lists.newArrayList();
		for (String id : ids) {
			childrens.add(evalSystemTreeRspMap.get(id));
		}

		return childrens;
	}

	private Map<String, EvalSystemTreeRsp> getMapByIndex(List<IndexContext> secondIndexes,
			List<IndexContext> thirdIndexes) {
		Map<String, EvalSystemTreeRsp> evalSystemTreeRspMap = Maps.newHashMap();
		for (IndexContext indexContext : secondIndexes) {
			EvalSystemTreeRsp evalSystemTreeRsp = convertIndexToEvalSystemTreeRsp(indexContext,
					EvalIndex.Level.SECOND.getCode());
			evalSystemTreeRspMap.put(evalSystemTreeRsp.getResourceId(), evalSystemTreeRsp);
		}

		for (IndexContext indexContext : thirdIndexes) {
			EvalSystemTreeRsp evalSystemTreeRsp = convertIndexToEvalSystemTreeRsp(indexContext,
					EvalIndex.Level.THIRD.getCode());
			evalSystemTreeRspMap.put(evalSystemTreeRsp.getResourceId(), evalSystemTreeRsp);
		}
		return evalSystemTreeRspMap;
	}

	private EvalSystemTreeRsp convertIndexToEvalSystemTreeRsp(IndexContext indexContext,
			Integer code) {
		EvalSystemTreeRsp evalSystemTreeRsp = new EvalSystemTreeRsp();
		evalSystemTreeRsp.setResourceId(indexContext.getIndexId());
		evalSystemTreeRsp.setLevel(code);
		evalSystemTreeRsp.setName(indexContext.getName());

		return evalSystemTreeRsp;
	}

}