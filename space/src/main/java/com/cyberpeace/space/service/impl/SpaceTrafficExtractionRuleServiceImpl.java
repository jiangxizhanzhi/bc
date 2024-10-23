
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.dao.mapper.SpaceTrafficExtractionRuleMapper;
import com.cyberpeace.space.service.SpaceTrafficExtractionRuleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceTrafficExtractionRuleServiceImpl extends ServiceImpl<SpaceTrafficExtractionRuleMapper, SpaceTrafficExtractionRule> implements SpaceTrafficExtractionRuleService

{}