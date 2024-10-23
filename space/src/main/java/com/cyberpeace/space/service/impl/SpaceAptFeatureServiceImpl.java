
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.dao.mapper.SpaceAptFeatureMapper;
import com.cyberpeace.space.service.SpaceAptFeatureService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceAptFeatureServiceImpl extends ServiceImpl<SpaceAptFeatureMapper, SpaceAptFeature> implements SpaceAptFeatureService

{}