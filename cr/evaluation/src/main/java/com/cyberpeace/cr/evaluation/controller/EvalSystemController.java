package com.cyberpeace.cr.evaluation.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

/**
 * 评估系统Controller
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSystemController` 的控制器类，用于处理与评估系统相关的HTTP请求。以下是对其主要部分的简要分析： 
 * ### 类和注解 
 * - **类定义**：`EvalSystemController` 继承了 `RestController` 注解，表示这是一个RESTful风格的控制器。 
 * - **权限控制**：使用了 `@RequiresAuthentication` 注解，表示需要用户认证才能访问这些接口。 
 * - **API文档**：使用了 `@Api` 和 `@ApiOperation` 注解来生成Swagger API文档。 
 * ### 成员变量 
 * - **依赖注入**： 
 * - `EvalIndexService`：用于操作评估指数。 
 * - `EvalCollectionMapper`：用于操作评估收集数据。 
 * - `EvalIndicatorService`：用于操作评估指示器。 
 * - `EvalSystemService`：用于操作评估系统。 
 * ### 主要方法 
 * - **`getEvalIndexTree` 方法**： 
 * - **功能**：根据资源ID查询评估系统的指标树。 
 * - **参数**：`resource_id`（模板ID）。 
 * - **流程**： 
 * 1. 通过 `evalSystemService` 获取评估系统信息。 
 * 2. 如果评估系统不存在，则抛出 `BusinessException` 异常。 
 * 3. 调用 `convertEvalSystemToEvalSystemTreeRsp` 方法将评估系统转换为 `EvalSystemTreeRsp` 响应对象。 
 * 4. 返回包含 `EvalSystemTreeRsp` 对象的 `BaseRsp` 响应。 
 * - **`convertEvalSystemToEvalSystemTreeRsp` 方法**： 
 * - **功能**：将评估系统信息转换为树形结构的响应对象。 
 * - **流程**： 
 * 1. 初始化 `EvalSystemTreeRsp` 对象。 
 * 2. 获取并处理一级、二级和三级指标。 
 * 3. 处理二级和三级指标的子节点。 
 * 4. 返回最终的树形结构。 
 * - **`getChildrens` 方法**： 
 * - **功能**：根据给定的ID列表获取子节点。 
 * - **流程**：遍历ID列表并将对应的 `EvalSystemTreeRsp` 对象添加到结果列表中。 
 * - **`getMapByIndex` 方法**： 
 * - **功能**：根据二级和三级指标创建一个映射。 
 * - **流程**：初始化映射，并将每个二级和三级指标转换为 `EvalSystemTreeRsp` 对象后放入映射中。 
 * - **`convertIndexToEvalSystemTreeRsp` 方法**： 
 * - **功能**：将 `IndexContext` 对象转换为 `EvalSystemTreeRsp` 对象。 
 * - **流程**：初始化 `EvalSystemTreeRsp` 对象并设置其属性。 
 * ### 总结 
 * 该控制器主要负责处理前端请求，将评估系统的指标信息以树形结构返回给客户端。通过依赖注入的方式，利用其他服务层的对象进行业务逻辑处理。整个流程清晰，职责明确，符合MVC架构的设计原则。 
**/
@Api(tags = "前台-测试靶场-获取树形指标体系")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/system_tree")
@Slf4j
public class EvalSystemController {

	/**
	 * 评估指数Service
	 */
	@Autowired
	private EvalIndexService evalIndexService;

	/**
	 * 评估收集Mapper
	 */
	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	/**
	 * 评估指示器Service
	 */
	@Autowired
	private EvalIndicatorService evalIndicatorService;

	/**
	 * 评估系统Service
	 */
	@Autowired
	private EvalSystemService evalSystemService;

	/**
	 * 获取评估指数树
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<EvalSystemTreeRsp> }
	 */
	@ApiOperation("查询体系指标")
	@GetMapping("")
	public BaseRsp<EvalSystemTreeRsp> getEvalIndexTree(
			@ApiParam(name = "resource_id", value = "模板id", required = true) String resource_id) {

		EvalSystem evalSystem = evalSystemService.getById(resource_id);
		if (null == evalSystem) {
			log.info("EvalSystem not found! EvalSystemId=[{}]", resource_id);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		EvalSystemTreeRsp evalSystemRsp = convertEvalSystemToEvalSystemTreeRsp(evalSystem);

		return new BaseRsp(evalSystemRsp);
	}

	/**
	 * 转换评估系统自评估系统树响应
	 *
	 * @param evalSystem 评估系统
	 * @return {@code EvalSystemTreeRsp }
	 */
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

	/**
	 * 获取儿童
	 *
	 * @param ids                  id列表
	 * @param evalSystemTreeRspMap 评估系统树响应地图
	 * @return {@code List<EvalSystemTreeRsp> }
	 */
	private List<EvalSystemTreeRsp> getChildrens(List<String> ids,
			Map<String, EvalSystemTreeRsp> evalSystemTreeRspMap) {
		List<EvalSystemTreeRsp> childrens = Lists.newArrayList();
		for (String id : ids) {
			childrens.add(evalSystemTreeRspMap.get(id));
		}

		return childrens;
	}

	/**
	 * 获取地图由指数
	 *
	 * @param secondIndexes 第二个索引
	 * @param thirdIndexes  第三索引
	 * @return {@code Map<String, EvalSystemTreeRsp> }
	 */
	private Map<String, EvalSystemTreeRsp> getMapByIndex(List<IndexContext> secondIndexes,
			List<IndexContext> thirdIndexes) {
		Map<String, EvalSystemTreeRsp> evalSystemTreeRspMap = evalSystemTreeRspMap = Maps
				.newHashMap();
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

	/**
	 * 转换指数自评估系统树响应
	 *
	 * @param indexContext 索引上下文
	 * @param code         代号
	 * @return {@code EvalSystemTreeRsp }
	 */
	private EvalSystemTreeRsp convertIndexToEvalSystemTreeRsp(IndexContext indexContext,
			Integer code) {
		EvalSystemTreeRsp evalSystemTreeRsp = new EvalSystemTreeRsp();
		evalSystemTreeRsp.setResourceId(indexContext.getIndexId());
		evalSystemTreeRsp.setLevel(code);
		evalSystemTreeRsp.setName(indexContext.getName());

		return evalSystemTreeRsp;
	}

}
