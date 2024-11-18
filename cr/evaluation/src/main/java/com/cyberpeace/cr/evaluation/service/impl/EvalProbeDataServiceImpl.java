package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalProbeData;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalProbeDataMapper;
import com.cyberpeace.cr.evaluation.service.EvalProbeDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 评估探针数据Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see EvalProbeDataService
 * @since 1.0.0
 */
@Slf4j
@Service
public class EvalProbeDataServiceImpl extends ServiceImpl<EvalProbeDataMapper, EvalProbeData> implements EvalProbeDataService {
}
