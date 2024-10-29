package com.cyberpeace.oj.practice.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationTarget;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationTargetMapper;
import com.cyberpeace.oj.practice.service.PracticeOperationTargetService;

@Service
public class PracticeOperationTargetServiceImpl extends ServiceImpl<PracticeOperationTargetMapper, PracticeOperationTarget> implements PracticeOperationTargetService {}
