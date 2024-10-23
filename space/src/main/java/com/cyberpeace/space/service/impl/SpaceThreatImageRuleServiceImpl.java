
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.dao.mapper.SpaceThreatImageRuleMapper;
import com.cyberpeace.space.service.SpaceThreatImageRuleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceThreatImageRuleServiceImpl extends ServiceImpl<SpaceThreatImageRuleMapper, SpaceThreatImageRule> implements SpaceThreatImageRuleService

{}