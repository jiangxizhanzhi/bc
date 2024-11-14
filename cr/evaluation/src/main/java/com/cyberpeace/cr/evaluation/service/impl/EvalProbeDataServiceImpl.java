package com.cyberpeace.cr.evaluation.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalProbeData;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalProbeDataMapper;
import com.cyberpeace.cr.evaluation.service.EvalProbeDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * 测评探针数据Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Service
public class EvalProbeDataServiceImpl extends ServiceImpl<EvalProbeDataMapper, EvalProbeData> implements EvalProbeDataService {
}
