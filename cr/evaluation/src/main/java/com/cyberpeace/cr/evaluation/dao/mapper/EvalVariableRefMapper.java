package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

/**
 * 测评变量裁判Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface EvalVariableRefMapper extends BaseMapper<EvalVariableRef> {
    /**
     * 通过评估获取id
     *
     * @param recordId
     * @return
     */
    List<String>  getVariableIdByRecordId(@Param("recordId") String recordId);

    /**
     * 通过评估获取测评
     *
     * @param recordId
     * @return
     */
    List<EvalVariableRef> getByRecordId(@Param("recordId") String recordId);


    /**
     * 获取变量id由记录id列表
     *
     * @param evalIndicatorIds 测评指示器id列表
     * @return {@code List<EvalVariableRef> }
     */
    List<EvalVariableRef> getVariableIdByRecordIds(List<String> evalIndicatorIds);
}
