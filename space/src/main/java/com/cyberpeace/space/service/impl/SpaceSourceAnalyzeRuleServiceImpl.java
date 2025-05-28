
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceSourceAnalyzeRule;
import com.cyberpeace.space.dao.mapper.SpaceSourceAnalyzeRuleMapper;
import com.cyberpeace.space.service.SpaceSourceAnalyzeRuleService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间源分析统治Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceSourceAnalyzeRuleService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceSourceAnalyzeRuleServiceImpl extends ServiceImpl<SpaceSourceAnalyzeRuleMapper, SpaceSourceAnalyzeRule> implements SpaceSourceAnalyzeRuleService

{}