package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionAttack;
import com.cyberpeace.space.dao.mapper.SpaceActionAttackMapper;
import com.cyberpeace.space.service.SpaceActionAttackService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceActionAttackServiceImpl extends ServiceImpl<SpaceActionAttackMapper, SpaceActionAttack> implements SpaceActionAttackService{
}