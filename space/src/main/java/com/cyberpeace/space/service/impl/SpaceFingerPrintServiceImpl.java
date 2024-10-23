package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceFingerPrint;
import com.cyberpeace.space.dao.mapper.SpaceFingerPrintMapper;
import com.cyberpeace.space.service.SpaceFingerPrintService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceFingerPrintServiceImpl extends ServiceImpl<SpaceFingerPrintMapper, SpaceFingerPrint> implements SpaceFingerPrintService {}
