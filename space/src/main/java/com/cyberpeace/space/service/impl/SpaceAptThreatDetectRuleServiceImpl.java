
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.dao.mapper.SpaceAptThreatDetectRuleMapper;
import com.cyberpeace.space.service.SpaceAptThreatDetectRuleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceAptThreatDetectRuleServiceImpl extends ServiceImpl<SpaceAptThreatDetectRuleMapper, SpaceAptThreatDetectRule> implements SpaceAptThreatDetectRuleService

{}