/**
 * 这段代码是一个基于Spring框架的Java服务类，实现了`SpaceRuleCommonService`接口。以下是代码的主要功能和结构分析： 
 * 1. **类的功能**： 
 * - 类名为`SpaceRuleCommonServiceImpl`，位于`com.cyberpeace.space.service.impl`包中。 
 * - 实现了`SpaceRuleCommonService`接口，提供了与空间规则相关的统计和服务功能。 
 * 2. **依赖注入**： 
 * - 使用了`@Autowired`注解将多个Mapper对象注入到类中，这些Mapper对象分别对应不同的数据库操作，如`SpaceScoreRankingRuleMapper`、`SpaceRoundMechanismRuleMapper`等。 
 * 3. **方法实现**： 
 * - `getRaceStatistics()`：获取比赛统计信息，包括得分排名、轮次机制和违规判断的数量，并返回一个`RaceStatistics`对象。 
 * - `getThreatDetectStatistics()`：获取威胁检测统计信息，包括流量检测、加载文件威胁、APT威胁等的数量，并返回一个`ThreatDetectStatistics`对象。 
 * - `getBaseDataStatistics()`：获取基础数据统计信息，包括APT特征等的数量，并返回一个`BaseDataRuleStatistics`对象。 
 * 4. **日志记录**： 
 * - 使用了`lombok`库中的`@Slf4j`注解，便于在类中使用SLF4J进行日志记录。 
 * 5. **注解**： 
 * - `@Service`：表明这是一个Spring服务类。 
 * - `@Slf4j`：启用SLF4J日志功能。 
 * 6. **未使用的Mapper**： 
 * - 注释掉了部分Mapper的使用，如`spaceTrafficExtractionRuleMapper`、`spaceThreatImageRuleMapper`等，可能是因为这些功能尚未实现或不需要当前使用。 
 * 总体而言，该类主要负责处理与空间规则相关的统计信息，并通过依赖注入的方式与其他数据访问层组件进行交互。 
**/
package com.cyberpeace.space.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.space.dao.mapper.*;
import com.cyberpeace.space.service.SpaceRuleCommonService;
import com.cyberpeace.space.service.context.rule.BaseDataRuleStatistics;
import com.cyberpeace.space.service.context.rule.RaceStatistics;
import com.cyberpeace.space.service.context.rule.ThreatDetectStatistics;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间统治常用Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceRuleCommonService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceRuleCommonServiceImpl implements SpaceRuleCommonService {

	@Autowired
	private SpaceScoreRankingRuleMapper spaceScoreRankingRuleMapper;

	@Autowired
	private SpaceRoundMechanismRuleMapper spaceRoundMechanismRuleMapper;

	@Autowired
	private SpaceViolationJudgeRuleMapper spaceViolationJudgeRuleMapper;

	@Autowired
	private SpaceTrafficExtractionRuleMapper spaceTrafficExtractionRuleMapper;

	@Autowired
	private SpaceTrafficDetectRuleMapper spaceTrafficDetectRuleMapper;

	@Autowired
	private SpaceLoadedFileThreatRuleMapper spaceLoadedFileThreatRuleMapper;

	@Autowired
	private SpaceAptThreatDetectRuleMapper spaceAptThreatDetectRuleMapper;

	@Autowired
	private SpaceThreatImageRuleMapper spaceThreatImageRuleMapper;

	@Autowired
	private SpaceEventIntegrationRuleMapper spaceEventIntegrationRuleMapper;

	@Autowired
	private SpaceSourceAnalyzeRuleMapper spaceSourceAnalyzeRuleMapper;

	@Autowired
	private SpaceFingerPrintMapper spaceFingerPrintMapper;

	@Autowired
	private SpaceAptFeatureMapper spaceAptFeatureMapper;

	@Autowired
	private SpaceIocThreatMapper spaceIocThreatMapper;

	/**
	 * 获取比赛统计学
	 *
	 * @return 比赛统计数据
	 * @throws
	 * @see RaceStatistics
	 */
	@Override
	public RaceStatistics getRaceStatistics() {
		Integer scoreRanking = spaceScoreRankingRuleMapper.selectCount(null);
		Integer roundMechanism = spaceRoundMechanismRuleMapper.selectCount(null);
		Integer violation = spaceViolationJudgeRuleMapper.selectCount(null);
		RaceStatistics res = new RaceStatistics();
		res.setScoreRanking(scoreRanking);
		res.setRoundMechanism(roundMechanism);
		res.setViolationJudge(violation);
		return res;
	}

	/**
	 * 获取威胁检测统计学
	 *
	 * @return 威胁检测统计信息
	 * @throws
	 * @see ThreatDetectStatistics
	 */
	@Override
	public ThreatDetectStatistics getThreatDetectStatistics() {
		ThreatDetectStatistics statistics = new ThreatDetectStatistics();
		statistics.setTrafficDetect(spaceTrafficDetectRuleMapper.selectCount(null));
//		statistics.setTrafficExtraction(spaceTrafficExtractionRuleMapper.selectCount(null));
		statistics.setLoadedFileThreat(spaceLoadedFileThreatRuleMapper.selectCount(null));
		statistics.setAptThreat(spaceAptThreatDetectRuleMapper.selectCount(null));
		statistics.setEventIntegration(spaceEventIntegrationRuleMapper.selectCount(null));
//		statistics.setThreatImage(spaceThreatImageRuleMapper.selectCount(null));
//		statistics.setSourceAnalyze(spaceSourceAnalyzeRuleMapper.selectCount(null));
		return statistics;
	}

	/**
	 * 获取基础数据统计学
	 *
	 * @return 基本数据规则统计信息
	 * @throws
	 * @see BaseDataRuleStatistics
	 */
	@Override
	public BaseDataRuleStatistics getBaseDataStatistics() {
		BaseDataRuleStatistics statistics = new BaseDataRuleStatistics();
//		statistics.setFingerPrint(spaceFingerPrintMapper.selectCount(null));
		statistics.setApt(spaceAptFeatureMapper.selectCount(null));
//		statistics.setIoc(spaceIocThreatMapper.selectCount(null));
		return statistics;
	}
}
