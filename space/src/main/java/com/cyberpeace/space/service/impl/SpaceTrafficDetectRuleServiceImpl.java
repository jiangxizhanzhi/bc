
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.dao.mapper.SpaceTrafficDetectRuleMapper;
import com.cyberpeace.space.service.SpaceTrafficDetectRuleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceTrafficDetectRuleServiceImpl extends ServiceImpl<SpaceTrafficDetectRuleMapper, SpaceTrafficDetectRule> implements SpaceTrafficDetectRuleService

{}