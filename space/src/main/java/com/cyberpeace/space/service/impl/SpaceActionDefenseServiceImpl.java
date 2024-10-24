package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceActionDefense;
import com.cyberpeace.space.dao.mapper.SpaceActionDefenseMapper;
import com.cyberpeace.space.service.SpaceActionDefenseService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceActionDefenseServiceImpl extends ServiceImpl<SpaceActionDefenseMapper, SpaceActionDefense> implements SpaceActionDefenseService{
}