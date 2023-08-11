package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
@Mapper
@Repository
public interface EvalRuleMapper extends BaseMapper<EvalRule> {

    int batchInsert(@Param("list") List<EvalRule> list);

}
