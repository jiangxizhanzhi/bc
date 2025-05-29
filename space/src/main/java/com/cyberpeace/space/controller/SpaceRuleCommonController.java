package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.service.SpaceRuleCommonService;
import com.cyberpeace.space.service.context.rule.BaseDataRuleStatistics;
import com.cyberpeace.space.service.context.rule.RaceStatistics;
import com.cyberpeace.space.service.context.rule.ThreatDetectStatistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 空间统治常用Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码是一个 Spring Boot 控制器类 `SpaceRuleCommonController`，用于处理与“知识空间-规则库-通用”相关的 HTTP 请求。以下是代码的简要分析： 
 * ### 主要功能 
 * 1. **初始化表信息**： 
 * - 在类加载时通过 `@PostConstruct` 注解调用 `init()` 方法，使用 `TableInfoHelper` 初始化 `SpaceBaseTable` 和 `SpaceFrameBaseTable` 的表信息。 
 * 2. **提供规则统计接口**： 
 * - 提供三个 RESTful API 接口，分别用于获取比赛规则、威胁规则和基础数据规则的统计信息。 
 * - 每个接口都返回一个包装了具体统计数据的 `BaseRsp` 对象。 
 * ### 关键点 
 * - **依赖注入**：通过 `@Autowired` 注入了 `SpaceRuleCommonService`，用于业务逻辑处理。 
 * - **Swagger 文档支持**：使用了 `io.swagger.annotations` 注解来生成 API 文档。 
 * - **异常处理**：虽然代码中没有显式定义异常处理逻辑，但返回值类型 `BaseRsp` 可能包含了错误信息。 
 * ### 总结 
 * 该控制器主要负责管理与规则统计相关的内容，通过初始化表信息并提供三个 REST 接口来满足特定的业务需求。 
**/
@Api(tags = "知识空间-规则库-通用")
@RestController
@RequestMapping("/space/rule/common")
public class SpaceRuleCommonController {

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	public void init() {
		TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
				SpaceBaseTable.class);
		TableInfoHelper.initTableInfo(new MapperBuilderAssistant(new MybatisConfiguration(), ""),
				SpaceFrameBaseTable.class);
	}

	/**
	 * Service
	 *
	 * @see SpaceRuleCommonService
	 */
	@Autowired
	private SpaceRuleCommonService service;

	/**
	 * 获取比赛规则
	 *
	 * @return
	 * @throws
	 * @see BaseRsp<RaceStatistics>
	 */
	@ApiOperation("比赛及训练评估规则统计")
	@GetMapping("/race/statistics")
	public BaseRsp<RaceStatistics> getRaceRuleS() {
		RaceStatistics res = service.getRaceStatistics();
		return new BaseRsp<>(res);
	}

	/**
	 * 获取威胁规则
	 *
	 * @return 基本 RSP< 威胁检测统计信息>
	 * @throws
	 * @see BaseRsp<ThreatDetectStatistics>
	 */
	@ApiOperation("威胁检测规则统计")
	@GetMapping("/threat/statistics")
	public BaseRsp<ThreatDetectStatistics> getThreatRuleS() {
		ThreatDetectStatistics res = service.getThreatDetectStatistics();
		return new BaseRsp<>(res);
	}

	/**
	 * 获取基础数据规则
	 *
	 * @return Base RSp< Base Data 规则统计信息>
	 * @throws
	 * @see BaseRsp<BaseDataRuleStatistics>
	 */
	@ApiOperation("基础数据规则统计")
	@GetMapping("/base_data/statistics")
	public BaseRsp<BaseDataRuleStatistics> getBaseDataRuleS() {
		BaseDataRuleStatistics res = service.getBaseDataStatistics();
		return new BaseRsp<>(res);
	}

}

