package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceAction;
import com.cyberpeace.space.dao.mapper.SpaceActionMapper;
import com.cyberpeace.space.service.SpaceActionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceActionServiceImpl extends ServiceImpl<SpaceActionMapper, SpaceAction> implements SpaceActionService {
}