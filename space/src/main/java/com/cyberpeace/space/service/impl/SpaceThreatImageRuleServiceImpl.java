
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.dao.mapper.SpaceThreatImageRuleMapper;
import com.cyberpeace.space.service.SpaceThreatImageRuleService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间威胁图像统治Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceThreatImageRuleService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceThreatImageRuleServiceImpl extends ServiceImpl<SpaceThreatImageRuleMapper, SpaceThreatImageRule> implements SpaceThreatImageRuleService

{}