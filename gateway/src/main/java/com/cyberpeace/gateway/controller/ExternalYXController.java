package com.cyberpeace.gateway.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlgorithmListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAlgorithmRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.cr.tool.controller.res.ToolTypeCount;
import com.cyberpeace.cr.tool.dao.context.MonthCount;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.gateway.controller.req.TheoryListReq;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.practice.controller.req.AdminOperationListReq;
import com.cyberpeace.oj.practice.controller.req.OperationByNodeReq;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.AdminOperationListBo;
import com.cyberpeace.oj.practice.service.bo.OperationListQueryBo;
import com.cyberpeace.oj.practice.service.bo.TheoryListQueryBo;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryBo;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameMapper;
import com.cyberpeace.space.service.context.frame.ActionStatisticsBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "外部接口-永信相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/yx")
public class ExternalYXController {

	@Autowired
	private PracticeCategoryService practiceCategoryService;

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;
	
	@Autowired
	private ToolService toolService;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private SpaceActionFrameMapper spaceActionFrameMapper;

	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	@ApiOperation("分页查询实践操作习题")
	@GetMapping("/operation/list")
	public BaseRsp<ListRsp<AdminOperationListBo>> list(AdminOperationListReq listReq) {

		OperationListQueryBo listQuery = BeanUtils.copy(listReq, OperationListQueryBo.class);

		if (StringUtils.isNotBlank(listReq.getCategoryId())) {
			List<String> categoryIds = practiceCategoryService
					.selectChildrenIds(listReq.getCategoryId(), CommonConstant.TRUE);
			listQuery.setCategoryIds(categoryIds);
		}

		Page page = listReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		IPage<AdminOperationListBo> iPage = practiceOperationService.pageByQuery(page, listQuery);

		return new BaseRsp<>(PageUtil.getListResult(iPage));
	}

	@ApiOperation("分页查询理论习题列表")
	@GetMapping("/theory/list")
	public BaseRsp<ListRsp<ExternalTheoryBo>> list(TheoryListReq pageReq) {

		Page page = pageReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		List<String> categoryIds = practiceCategoryService
				.selectChildrenIds(pageReq.getCategoryId(), CommonConstant.TRUE);

		TheoryListQueryBo listQuery = BeanUtils.copy(pageReq, TheoryListQueryBo.class);
		listQuery.setCategoryIds(categoryIds);

		IPage<ExternalTheoryBo> boIPage = practiceTheoryService.pageByQuery(page, listQuery);

		return new BaseRsp<>(PageUtil.getListResult(boIPage));

	}

	@ApiOperation("根据镜像id获取实操题列表")
	@PostMapping("/list/by_node")
	public BaseRsp<List<AdminOperationListBo>> getOperationByNode(@RequestBody OperationByNodeReq operationByNodeReq) {
		return new BaseRsp<>(practiceOperationService.getOperationByBodeIds(operationByNodeReq.getNodeIds()));
	}

	@ApiOperation("工具统计")
	@GetMapping("/tools/statistics")
	public BaseRsp<List<ToolTypeCount>> toolStatistics() {
		return new BaseRsp<>(toolService.statistics());
	}

	@ApiOperation("知识库统计")
	@GetMapping("/knowledge/statistics")
	public BaseRsp<List<ToolTypeCount>> knowledgeStatistics() {
		List<ToolTypeCount> statisticsBos = new ArrayList<>();
		ToolTypeCount courseBo = new ToolTypeCount();
		courseBo.setName("课程");
		List<String> timeList = courseMapper.selectTime();
		courseBo.setCount(timeList.size());
		courseBo.setCountList(statistics(timeList));
		statisticsBos.add(courseBo);


		ToolTypeCount theory = new ToolTypeCount();
		theory.setName("理论");
		timeList = practiceTheoryMapper.selectTime();
		theory.setCount(timeList.size());
		theory.setCountList(statistics(timeList));
		statisticsBos.add(theory);

		ToolTypeCount operation = new ToolTypeCount();
		operation.setName("实操");
		timeList = practiceOperationMapper.selectTime();
		operation.setCount(timeList.size());
		operation.setCountList(statistics(timeList));
		statisticsBos.add(operation);

		ToolTypeCount total = new ToolTypeCount();
		total.setName("总计");
		Integer sum = 0;
		Map<String, Integer> monthCountMap = new HashMap<>();
		for (ToolTypeCount typeCount : statisticsBos) {
			sum += typeCount.getCount();
			for (MonthCount monthCount : typeCount.getCountList()) {
				 monthCountMap.put(monthCount.getMonth(), monthCountMap.getOrDefault(monthCount.getMonth(), 0) + monthCount.getCount());
			}
		}
		List<MonthCount> monthCountList = new ArrayList<>();
		total.setCount(sum);
		for (String month : monthCountMap.keySet()) {
			monthCountList.add(new MonthCount(month, monthCountMap.getOrDefault(month, 0)));
		}
		total.setCountList(monthCountList);
		statisticsBos.add(total);

		return new BaseRsp<>(statisticsBos);
	}

	private List<MonthCount> statistics(List<String> timeList) {
		List<MonthCount> monthCounts = new ArrayList<>();
		for (int i = 0 ; i < 6 ; i++) {
			String month = TimeUtil.format11(TimeUtil.addMonths(new Date(), -i));
			monthCounts.add(new MonthCount(month, (int) timeList.stream().filter(month::equals).count()));
		}
		return monthCounts;
	}

	@ApiOperation("行动框架统计")
	@GetMapping("/frame/statistics")
	public BaseRsp<List<ToolTypeCount>> frameStatistics() {
		List<ActionStatisticsBo> statisticsBos = spaceActionFrameMapper.statistics();
		List<ToolTypeCount> countList = new ArrayList<>();
		for (SpaceActionFrame.Type type : SpaceActionFrame.Type.values()) {
			ToolTypeCount toolTypeCount = new ToolTypeCount();
			toolTypeCount.setName(type.getDesc());
			List<ActionStatisticsBo> bos = statisticsBos.stream().filter(bo -> type.getCode().equals(bo.getType())).collect(Collectors.toList());
			toolTypeCount.setCount(bos.size());
			List<MonthCount> monthCounts = new ArrayList<>();
			for (int i = 0 ; i < 6 ; i++) {
				String month = TimeUtil.format11(TimeUtil.addMonths(new Date(), -i));
				monthCounts.add(new MonthCount(month, (int) bos.stream().filter(bo -> month.equals(bo.getDate())).count()));
			}
			toolTypeCount.setCountList(monthCounts);
			countList.add(toolTypeCount);
		}


		ToolTypeCount toolTypeCount = new ToolTypeCount();
		toolTypeCount.setName("总计");
		toolTypeCount.setCount(statisticsBos.size());
		List<MonthCount> monthCounts = new ArrayList<>();
		for (int i = 0 ; i < 6 ; i++) {
			String month = TimeUtil.format11(TimeUtil.addMonths(new Date(), -i));
			monthCounts.add(new MonthCount(month, (int) statisticsBos.stream().filter(tool -> month.equals(tool.getDate())).count()));
		}
		toolTypeCount.setCountList(monthCounts);
		countList.add(toolTypeCount);
		return new BaseRsp<>(countList);
	}

	@ApiOperation("列表查询")
	@GetMapping("/algorithm/list")
	public BaseRsp<ListRsp<EvalAlgorithmRsp>> list(EvalAlgorithmListReq listReq) {
		EvalListQuery listQuery = BeanUtils.copy(listReq, EvalListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		listQuery.setName(listReq.getPattern());

		// 默认时间排序
		listQuery.setSort("-create_time");

		IPage<EvalAlgorithm> data = evalAlgorithmService.page(listQuery);

		data.convert(evalAlgorithm -> {
			EvalAlgorithmRsp evalAlgorithmRsp = BeanUtils.copy(evalAlgorithm,
					EvalAlgorithmRsp.class);
			evalAlgorithmRsp.setEvalAlgorithmId(evalAlgorithm.getResourceId());
			evalAlgorithmRsp.setParams(JsonUtil.toJsonObjectArray(evalAlgorithm.getParams(),
					AlgorithmParamContext.class));
			evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

			return evalAlgorithmRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	@ApiOperation("查询算法")
	@GetMapping("/algorithm")
	public BaseRsp<EvalAlgorithmRsp> getEvalAlgorithmRsp(
			@ApiParam(name = "resource_id", value = "id", required = true) String resource_id) {

		EvalAlgorithm evalAlgorithm = evalAlgorithmService.getById(resource_id);
		if (null == evalAlgorithm) {
			log.info("EvalAlgorithm not found! evalAlgorithmId=[{}]", resource_id);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		EvalAlgorithmRsp evalAlgorithmRsp = BeanUtils.copy(evalAlgorithm, EvalAlgorithmRsp.class);
		evalAlgorithmRsp.setEvalAlgorithmId(evalAlgorithm.getResourceId());
		evalAlgorithmRsp.setParams(
				JsonUtil.toJsonObjectArray(evalAlgorithm.getParams(), AlgorithmParamContext.class));
		evalAlgorithmRsp.setParamsNum(evalAlgorithmRsp.getParams().size());

		return new BaseRsp(evalAlgorithmRsp);
	}

}
