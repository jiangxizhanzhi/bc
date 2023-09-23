package com.cyberpeace.cr.evaluation.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalProbeData;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalProbeDataMapper;
import com.cyberpeace.cr.evaluation.service.EvalProbeDataService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvalProbeDataServiceImpl extends ServiceImpl<EvalProbeDataMapper, EvalProbeData> implements EvalProbeDataService {
}
