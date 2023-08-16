package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

@Mapper
@Repository
public interface EvalVariableRefMapper extends BaseMapper<EvalVariableRef> {
    /**
     * 通过评估获取id
     * @param recordId
     * @return
     */
    List<String>  getVariableIdByRecordId(@Param("recordId") String recordId);

    /**
     * 通过评估获取测评
     * @param recordId
     * @return
     */
    List<EvalVariableRef> getByRecordId(@Param("recordId") String recordId);



    List<EvalVariableRef> getVariableIdByRecordIds(List<String> evalIndicatorIds);
}
