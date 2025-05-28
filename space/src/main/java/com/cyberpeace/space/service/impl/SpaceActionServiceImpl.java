package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.dao.mapper.SpaceActionMapper;
import com.cyberpeace.space.service.SpaceActionService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间行动Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceActionServiceImpl extends ServiceImpl<SpaceActionMapper, SpaceAction> implements SpaceActionService {
}