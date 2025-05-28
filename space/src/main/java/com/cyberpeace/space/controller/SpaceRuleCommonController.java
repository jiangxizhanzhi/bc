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
