package com.cyberpeace.gateway.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.CallCount;
import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.OrderNameConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlgorithmListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRunAlgorithmReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalAlgorithmRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.evaluation.service.context.AlgorithmParamContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.cyberpeace.cr.tool.controller.res.ToolTypeCount;
import com.cyberpeace.cr.tool.dao.context.MonthCount;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.dao.mapper.AttachmentMapper;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.gateway.controller.req.TheoryListReq;
import com.cyberpeace.gateway.service.bo.ExternalOperationBo;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.cyberpeace.oj.course.service.bo.CourseWebBo;
import com.cyberpeace.oj.practice.controller.req.AdminOperationListReq;
import com.cyberpeace.oj.practice.controller.req.AdminTheoryListReq;
import com.cyberpeace.oj.practice.controller.req.OperationByNodeReq;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryListReq;
import com.cyberpeace.oj.practice.controller.req.external.PracticeRandomChoiceReq;
import com.cyberpeace.oj.practice.dao.context.OperationListQuery;
import com.cyberpeace.oj.practice.dao.context.TheoryListQuery;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.*;
import com.cyberpeace.oj.practice.service.externalbo.ExternalTheoryBo;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameMapper;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.frame.ActionStatisticsBo;
import com.cyberpeace.space.service.context.strategy.SpaceStrategyBo;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 外部 YXCiController
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ExternalYXController` 的 Spring MVC 控制器类，用于处理与外部系统（如永信）相关的 API 请求。以下是对该代码的主要功能和结构的简要分析： 
 * ### 主要功能 
 * 1. **分页查询**： 
 * - 提供了多个方法用于分页查询不同类型的资源，如实践操作、理论题目、分类等。 
 * - 示例方法：`list(AdminOperationListReq listReq)` 和 `list(TheoryListReq pageReq)`。 
 * 2. **获取单个资源**： 
 * - 可以通过特定的 ID 获取某个实践操作或理论题目的详细信息。 
 * - 示例方法：`get(@RequestParam("resource_id") String resourceId)`。 
 * 3. **统计信息**： 
 * - 提供了多种统计方法，如工具统计、知识库统计、行动框架统计等。 
 * - 示例方法：`toolStatistics()` 和 `knowledgeStatistics()`。 
 * 4. **随机选择题目**： 
 * - 支持根据类别随机选择实践操作或理论题目。 
 * - 示例方法：`getTheoryRandom(PracticeRandomChoiceReq queryParams)` 和 `getOperationRandom(PracticeRandomChoiceReq choiceReq)`。 
 * 5. **运行算法**： 
 * - 提供了执行评估算法的方法，并返回相应的结果。 
 * - 示例方法：`runAlgorithm(@RequestBody EvalRunAlgorithmReq evalRunAlgorithmReq)`。 
 * 6. **其他功能**： 
 * - 包括记录策略使用情况、导出策略、查看策略详情等功能。 
 * - 示例方法：`useActionFrame(@PathVariable String type)` 和 `exportStrategy2PDF(HttpServletResponse httpServletResponse, List<String> strategy_ids)`。 
 * ### 结构特点 
 * 1. **依赖注入**： 
 * - 使用了 Spring 的 `@Autowired` 注解来注入各种服务和数据访问对象（如 `PracticeCategoryService`、`PracticeTheoryService` 等）。 
 * 2. **响应封装**： 
 * - 所有对外的 API 响应都封装在 `BaseRsp` 对象中，以统一的格式返回给客户端。 
 * 3. **Swagger 文档注解**： 
 * - 使用了 Swagger 注解（如 `@Api`、`@ApiOperation`）来生成 API 文档。 
 * 4. **日志记录**： 
 * - 使用了 Lombok 的 `@Slf4j` 注解来简化日志记录。 
 * 5. **数据转换**： 
 * - 使用了工具类（如 `BeanUtils`）来进行对象之间的转换。 
 * ### 总结 
 * `ExternalYXController` 是一个多功能的控制器类，主要用于处理与外部系统的交互请求，提供了丰富的查询、统计、随机选择和算法执行等功能。代码结构清晰，依赖注入和响应封装良好，便于维护和扩展。 
**/
@Api(tags = "外部接口-永信相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/yx")
public class ExternalYXController {

	/**
	 * 实践类别Service
	 */
	@Autowired
	private PracticeCategoryService practiceCategoryService;

	/**
	 * 实践实操Service
	 */
	@Autowired
	private PracticeOperationService practiceOperationService;

	/**
	 * 实践理论Service
	 */
	@Autowired
	private PracticeTheoryService practiceTheoryService;

	/**
	 * 工具Service
	 */
	@Autowired
	private ToolService toolService;

	/**
	 * 实践理论Mapper
	 */
	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	/**
	 * 实践实操Mapper
	 */
	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	/**
	 * 课程Mapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 空间行动框架Mapper
	 */
	@Autowired
	private SpaceActionFrameMapper spaceActionFrameMapper;

	/**
	 * 空间行动框架service
	 */
	@Autowired
	private SpaceActionFrameService spaceActionFrameService;

	/**
	 * 评估算法Service
	 */
	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	/**
	 * 实践类别Mapper
	 */
	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	/**
	 * 附件Mapper
	 */
	@Autowired
	private AttachmentMapper attachmentMapper;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 课程service
	 */
	@Autowired
	private CourseService courseService;
	/**
	 * 策略service
	 */
	@Autowired
	private SpaceStrategyService spaceStrategyService;

	/**
	 * 列表
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<AdminOperationListBo>> }
	 */
	@ApiOperation("分页查询实践操作习题")
	@GetMapping("/operation/list")
	@CallCount(CallType.THEORY)
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

	@GetMapping("/practice/operation")
	@ApiOperation("获取单个实践操作")
	@CallCount(CallType.THEORY)
	public BaseRsp<WebOperationBo> get(@RequestParam("resource_id") String resourceId) {
		WebOperationBo operationBo = practiceOperationService.selectById(resourceId);

		return new BaseRsp<>(operationBo);
	}

	/**
	 * 列表
	 *
	 * @param pageReq 分页请求体
	 * @return {@code BaseRsp<ListRsp<ExternalTheoryBo>> }
	 */
	@ApiOperation("分页查询理论习题列表")
	@GetMapping("/theory/list")
	@CallCount(CallType.THEORY)
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

	/**
	 * 获取实操由节点
	 *
	 * @param operationByNodeReq 实操由节点请求体
	 * @return {@code BaseRsp<List<AdminOperationListBo>> }
	 */
	@ApiOperation("根据镜像id获取实操题列表")
	@PostMapping("/list/by_node")
	@CallCount(CallType.THEORY)
	public BaseRsp<List<AdminOperationListBo>> getOperationByNode(@RequestBody OperationByNodeReq operationByNodeReq) {
		return new BaseRsp<>(practiceOperationService.getOperationByBodeIds(operationByNodeReq.getNodeIds()));
	}

	/**
	 * 工具统计
	 *
	 * @return {@code BaseRsp<List<ToolTypeCount>> }
	 */
	@ApiOperation("工具统计")
	@GetMapping("/tools/statistics")
	public BaseRsp<List<ToolTypeCount>> toolStatistics() {
		return new BaseRsp<>(toolService.statistics());
	}

	/**
	 * 知识统计
	 *
	 * @return {@code BaseRsp<List<ToolTypeCount>> }
	 */
	@ApiOperation("知识库统计")
	@GetMapping("/knowledge/statistics")
	@CallCount(CallType.THEORY)
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

	/**
	 * 统计学
	 *
	 * @param timeList 时间列表
	 * @return {@code List<MonthCount> }
	 */
	private List<MonthCount> statistics(List<String> timeList) {
		List<MonthCount> monthCounts = new ArrayList<>();
		for (int i = 0 ; i < 6 ; i++) {
			String month = TimeUtil.format11(TimeUtil.addMonths(new Date(), -i));
			monthCounts.add(new MonthCount(month, (int) timeList.stream().filter(month::equals).count()));
		}
		return monthCounts;
	}

	/**
	 * 框架统计学
	 *
	 * @return {@code BaseRsp<List<ToolTypeCount>> }
	 */
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

	/**
	 * 列表
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<EvalAlgorithmRsp>> }
	 */
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

	/**
	 * 获取评估算法响应
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<EvalAlgorithmRsp> }
	 */
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

	/**
	 * run 算法
	 *
	 * @param evalRunAlgorithmReq 评估跑算法请求体
	 * @return {@code BaseRsp<Object> }
	 */
	@ApiOperation("执行算法")
	@PostMapping(("/algorithm/run"))
	public BaseRsp<Object> runAlgorithm(@RequestBody EvalRunAlgorithmReq evalRunAlgorithmReq) {
		return new BaseRsp<>(evalAlgorithmService.runAlgorithm(evalRunAlgorithmReq.getAlgorithmId(), evalRunAlgorithmReq.getData(), evalRunAlgorithmReq.getListData(), evalRunAlgorithmReq.getOtherArgs()));
	}

	/**
	 * 列表
	 *
	 * @param categoryListReq 类别列表请求体
	 * @return {@code BaseRsp<ListRsp<CategoryListBo>> }
	 */
	@ApiOperation("分页查询练习分类")
	@GetMapping("/category/list")
	@CallCount(CallType.THEORY)
	public BaseRsp<ListRsp<CategoryListBo>> list(@Validated PracticeCategoryListReq categoryListReq) {

		Page page = categoryListReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}
		CategoryListQuery query = BeanUtils.copy(categoryListReq, CategoryListQuery.class);

		IPage<CategoryListBo> iPage = practiceCategoryService.pageByListQuery(page, query,
				categoryListReq.getChildren());

		return new BaseRsp(PageUtil.getListResult(iPage));
	}

	/**
	 * 获取理论随机
	 *
	 * @param queryParams 查询参数
	 * @return {@code BaseRsp<List<PracticeTheory>> }
	 */
	@ApiOperation("理论基础题库-随机获取数条")
	@GetMapping("/theory/random-choice")
	@CallCount(CallType.THEORY)
	public BaseRsp<List<PracticeTheory>> getTheoryRandom(PracticeRandomChoiceReq queryParams) {

		TheoryListQuery listQuery = BeanUtils.copy(queryParams, TheoryListQuery.class);
		if (org.springframework.util.StringUtils.hasText(queryParams.getCategory())) {
			listQuery.setCategoryIds(Lists.newArrayList(queryParams.getCategory()));
		}

		List<PracticeTheory> result = practiceTheoryMapper.randomChoice(listQuery,
				queryParams.getCount());
		for (PracticeTheory item : result) {
			item.setScore(queryParams.getScore());
		}
		return new BaseRsp<>(result);
	}

	/**
	 * 获取实操随机
	 *
	 * @param choiceReq 选择请求体
	 * @return {@code BaseRsp<List<ExternalOperationBo>> }
	 */
	@ApiOperation("实操习题随机获取数条")
	@GetMapping("/operation/random-choice")
	@CallCount(CallType.THEORY)
	public BaseRsp<List<ExternalOperationBo>> getOperationRandom(PracticeRandomChoiceReq choiceReq) {

		OperationListQuery listQuery = BeanUtils.copy(choiceReq, OperationListQuery.class);
		if (org.springframework.util.StringUtils.hasText(choiceReq.getCategory())) {
			listQuery.setCategoryIds(Lists.newArrayList(choiceReq.getCategory()));
		}

		List<PracticeOperation> operationList = practiceOperationMapper.randomChoice(listQuery,
				choiceReq.getCount());
		List<ExternalOperationBo> boList = StreamApiUtils.map(operationList,
				operation -> entityToExternal(operation));
		for (ExternalOperationBo operationBo : boList) {
			operationBo.setScore(choiceReq.getScore());
		}

		return new BaseRsp<>(boList);
	}

	/**
	 * 实体类转换为外部字段
	 *
	 * @param operation
	 * @return
	 */
	private ExternalOperationBo entityToExternal(PracticeOperation operation) {
		ExternalOperationBo externalOperationBo = BeanUtils.copy(operation,
				ExternalOperationBo.class);
		externalOperationBo.setCreateUser(operation.getCreateUserId());

		// 分类
		PracticeCategory practiceCategory = practiceCategoryMapper
				.selectById(operation.getCategoryId());
		if (null != practiceCategory) {
			externalOperationBo.setCategoryInfo(practiceCategory.getName());
		}

		if (org.springframework.util.StringUtils.hasText(operation.getAttachmentId())) {
			if (null != operation.getDynamicFlag() && operation.getDynamicFlag()
					.intValue() == PracticeOperation.DynamicFlag.RANDOM.getCode().intValue()) {

				List<PracticeOperationFileBo> fileBos = practiceOperationService
						.getOperationFile(operation.getAttachmentId());
				externalOperationBo.setAttachment(fileBos);

			} else {
				Attachment attachment = attachmentMapper.selectById(operation.getAttachmentId());
				String fileUrl = utilService.handlerFileUrl(attachment.getFilePath());
				externalOperationBo.setFileUrl(fileUrl);
				externalOperationBo.setFileName(attachment.getFileName());
			}

		}

		externalOperationBo.setIsDynamicEnv(operation.getDynamicEnv());
		externalOperationBo.setIsDynamicFlag(operation.getDynamicFlag());
		externalOperationBo.setShareMode(operation.getSceneShareMode());

		return externalOperationBo;
	}

	/**
	 * 根据方向获取课程
	 *
	 * @return {@code BaseRsp<ListRsp<CourseWebBo>> }
	 */
	@ApiOperation("根据方向获取课程")
	@GetMapping("/course/course_category")
	@CallCount(CallType.THEORY)
	public BaseRsp<ListRsp<CourseWebBo>> getCourseByCategory(
			@ApiParam(name = "listReq", value = "查询条件") @Valid CourseListReq courseListReq) {
		// 组装筛选条件
		CourseQueryBo context = BeanUtils.copy(courseListReq, CourseQueryBo.class);
		context.setDirections(Collections.singletonList(courseListReq.getDirection()));
		return new BaseRsp<>(courseService.selectPage(courseListReq.toPageParam(), context));
	}

	/**
	 * 根据方向获取理论题
	 *
	 * @return {@code BaseRsp<List<ExternalOperationBo>> }
	 */
	@ApiOperation("根据岗位方向获取理论题")
	@GetMapping("/theory/course_category")
	@CallCount(CallType.THEORY)
	public BaseRsp<ListRsp<ExternalTheoryBo>> getTheoryByCategory(
			@ApiParam(name = "listReq", value = "查询条件") @Valid AdminTheoryListReq theoryListReq) {
		Page page = theoryListReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		List<String> categoryIds = practiceCategoryService
				.selectChildrenIds(theoryListReq.getCategoryId(), CommonConstant.TRUE);

		TheoryListQueryBo listQuery = BeanUtils.copy(theoryListReq, TheoryListQueryBo.class);
		listQuery.setCategoryIds(categoryIds);

		IPage<ExternalTheoryBo> boIPage = practiceTheoryService.pageByQuery(page, listQuery);
		return new BaseRsp<>(PageUtil.getListResult(boIPage));
	}

	@ApiOperation("理论知识库使用次数+1")
	@GetMapping("/use/theory")
	@CallCount(CallType.THEORY)
	public BaseRsp<Boolean> useTheory() {
		return new BaseRsp<>(true);
	}

	@ApiOperation("策略库使用次数+1")
	@GetMapping("/use/strategy")
	@CallCount(CallType.STRATEGY)
	public BaseRsp<Boolean> useStrategy() {
		return new BaseRsp<>(true);
	}

	@ApiOperation("战法库使用次数+1")
	@GetMapping("/use/tactic")
	@CallCount(CallType.TACTIC)
	public BaseRsp<Boolean> useTactic() {
		return new BaseRsp<>(true);
	}

	// 漏洞库服务详情访问后，也会调用统计次数加+1
	@ApiOperation("规则库使用次数+1")
	@GetMapping("/use/rule")
	@CallCount(CallType.RULE)
	public BaseRsp<Boolean> useRule() {
		return new BaseRsp<>(true);
	}

	@ApiOperation("算法库使用次数+1")
	@GetMapping("/use/algorithm")
	@CallCount(CallType.ALGORITHM)
	public BaseRsp<Boolean> useAlgorithm() {
		return new BaseRsp<>(true);
	}

	@ApiOperation("记录行动框架使用")
	@GetMapping("/use/action/frame/{type}")
	public BaseRsp<Boolean> useActionFrame(@ApiParam(name = "type",
			value = "框架类型，process or target", required = true) @PathVariable String type) {
		spaceActionFrameService.recordUsage(type, 1);
		return new BaseRsp<>(true);
	}
	@ApiOperation("导出策略")
	@GetMapping("/strategy/export")
	public void exportStrategy2PDF(
			HttpServletResponse httpServletResponse,
			@ApiParam(value = "策略ids", name = "strategy_ids") @RequestParam List<String> strategy_ids) {
		FileUtil.setResponse(new File(spaceStrategyService.dumpStrategyPath(strategy_ids)), false, httpServletResponse);
	}
	@ApiOperation("查看策略详情")
	@GetMapping("/strategy/detail")
	@CallCount(CallType.STRATEGY)
	public BaseRsp<SpaceStrategyBo>
	getDetail(@ApiParam(value = "策略id", name = "resource_id") String resource_id) {
		SpaceStrategyBo res = spaceStrategyService.getDetail(resource_id);
		return new BaseRsp<>(res);
	}

}

