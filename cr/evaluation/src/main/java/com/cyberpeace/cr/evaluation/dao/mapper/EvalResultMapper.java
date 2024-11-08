package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 评估结果Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface EvalResultMapper extends BaseMapper<EvalResult> {
}




