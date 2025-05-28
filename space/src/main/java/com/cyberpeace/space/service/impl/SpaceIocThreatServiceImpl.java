
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.dao.mapper.SpaceIocThreatMapper;
import com.cyberpeace.space.service.SpaceIocThreatService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间国际 奥委会威胁Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceIocThreatService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceIocThreatServiceImpl extends ServiceImpl<SpaceIocThreatMapper, SpaceIocThreat> implements SpaceIocThreatService

{}