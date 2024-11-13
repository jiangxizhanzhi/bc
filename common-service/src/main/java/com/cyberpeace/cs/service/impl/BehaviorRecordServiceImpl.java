package com.cyberpeace.cs.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cs.dao.entity.BehaviorRecordEntity;
import com.cyberpeace.cs.dao.mapper.BehaviorRecordMapper;
import com.cyberpeace.cs.service.BehaviorRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * 行为记录Service实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Service
@Slf4j
public class BehaviorRecordServiceImpl extends ServiceImpl<BehaviorRecordMapper, BehaviorRecordEntity> implements BehaviorRecordService {

}

