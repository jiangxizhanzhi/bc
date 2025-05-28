package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionMethod;
import com.cyberpeace.space.dao.mapper.SpaceActionMethodMapper;
import com.cyberpeace.space.service.SpaceActionMethodService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间行动方法Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionMethodService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceActionMethodServiceImpl extends ServiceImpl<SpaceActionMethodMapper, SpaceActionMethod> implements SpaceActionMethodService{
}