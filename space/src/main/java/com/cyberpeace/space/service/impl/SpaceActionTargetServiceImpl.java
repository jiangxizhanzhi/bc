package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.dao.mapper.SpaceActionTargetMapper;
import com.cyberpeace.space.service.SpaceActionTargetService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间行动目标Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionTargetService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceActionTargetServiceImpl extends ServiceImpl<SpaceActionTargetMapper, SpaceActionTarget> implements SpaceActionTargetService{
}