package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.dao.mapper.SpaceActionTargetMapper;
import com.cyberpeace.space.service.SpaceActionTargetService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceActionTargetServiceImpl extends ServiceImpl<SpaceActionTargetMapper, SpaceActionTarget> implements SpaceActionTargetService{
}