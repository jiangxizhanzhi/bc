
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.dao.mapper.SpaceTrafficExtractionRuleMapper;
import com.cyberpeace.space.service.SpaceTrafficExtractionRuleService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间流量萃取统治Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceTrafficExtractionRuleService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceTrafficExtractionRuleServiceImpl extends ServiceImpl<SpaceTrafficExtractionRuleMapper, SpaceTrafficExtractionRule> implements SpaceTrafficExtractionRuleService

{}