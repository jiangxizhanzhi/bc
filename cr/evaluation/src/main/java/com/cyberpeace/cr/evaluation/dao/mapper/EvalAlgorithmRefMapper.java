package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 评估算法裁判Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalAlgorithmRefMapper extends BaseMapper<EvalAlgorithmRef> {
}
