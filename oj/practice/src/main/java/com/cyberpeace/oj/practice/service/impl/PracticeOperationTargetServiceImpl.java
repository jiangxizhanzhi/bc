package com.cyberpeace.oj.practice.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationTarget;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationTargetMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationTargetService;

/**
 * 实践实操目标Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class PracticeOperationTargetServiceImpl extends ServiceImpl<PracticeOperationTargetMapper, PracticeOperationTarget> implements PracticeOperationTargetService {}
