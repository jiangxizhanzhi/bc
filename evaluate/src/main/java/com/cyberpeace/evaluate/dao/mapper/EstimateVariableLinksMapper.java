package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.EstimateVariableLinks;

@Mapper
public interface EstimateVariableLinksMapper {

    void insert(EstimateVariableLinks estimateVariableLinks);

    /**
     * 更新评估变量项关联表
     * @param estimateVariableLinks
     */
    void update(EstimateVariableLinks estimateVariableLinks);

    List<EstimateVariableLinks> selectByEstimateId(String estimateId);

    EstimateVariableLinks getOneLink(@Param("estimateId") String estimateId, @Param("variableId") String variableId);
}
