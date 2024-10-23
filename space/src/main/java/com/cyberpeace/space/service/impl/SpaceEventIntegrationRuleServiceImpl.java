
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.dao.mapper.SpaceEventIntegrationRuleMapper;
import com.cyberpeace.space.service.SpaceEventIntegrationRuleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceEventIntegrationRuleServiceImpl extends ServiceImpl<SpaceEventIntegrationRuleMapper, SpaceEventIntegrationRule> implements SpaceEventIntegrationRuleService

{}