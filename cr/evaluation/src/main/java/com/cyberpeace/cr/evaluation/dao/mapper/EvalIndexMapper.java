package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
public interface EvalIndexMapper extends BaseMapper<EvalIndex> {

    List<EvalIndex> selectListBySystemIdAndLevel(String systemId, Integer level);
}




