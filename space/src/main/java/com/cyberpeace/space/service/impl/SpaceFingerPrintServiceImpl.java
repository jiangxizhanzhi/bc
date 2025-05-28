package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceFingerPrint;
import com.cyberpeace.space.dao.mapper.SpaceFingerPrintMapper;
import com.cyberpeace.space.service.SpaceFingerPrintService;

import lombok.extern.slf4j.Slf4j;

/**
 * 空间手指打印Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceFingerPrintService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceFingerPrintServiceImpl extends ServiceImpl<SpaceFingerPrintMapper, SpaceFingerPrint> implements SpaceFingerPrintService {}
