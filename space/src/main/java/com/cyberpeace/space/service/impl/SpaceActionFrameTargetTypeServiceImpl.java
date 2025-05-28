
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionFrameTargetType;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameTargetTypeMapper;
import com.cyberpeace.space.service.SpaceActionFrameTargetTypeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间行动框架目标类型Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionFrameTargetTypeService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceActionFrameTargetTypeServiceImpl extends ServiceImpl<SpaceActionFrameTargetTypeMapper, SpaceActionFrameTargetType> implements SpaceActionFrameTargetTypeService {
}

    