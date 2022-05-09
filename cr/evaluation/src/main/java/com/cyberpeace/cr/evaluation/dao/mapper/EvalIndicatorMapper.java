package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator
 */
public interface EvalIndicatorMapper extends BaseMapper<EvalIndicator> {

	Integer selectAlgorithmSum(List<String> algorithmIds);
}
