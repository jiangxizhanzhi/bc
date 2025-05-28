
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionFrameEventType;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameEventTypeMapper;
import com.cyberpeace.space.service.SpaceActionFrameEventTypeService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间行动框架事件类型Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionFrameEventTypeService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceActionFrameEventTypeServiceImpl extends ServiceImpl<SpaceActionFrameEventTypeMapper, SpaceActionFrameEventType> implements SpaceActionFrameEventTypeService {
}

    