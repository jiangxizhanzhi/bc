package com.cyberpeace.cr.evaluation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalResult;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalResult
 */
@Mapper
@Repository
public interface EvalResultMapper extends BaseMapper<EvalResult> {
}




