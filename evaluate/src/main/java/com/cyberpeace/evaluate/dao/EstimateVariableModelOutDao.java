package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateVariableModelOut;

public interface EstimateVariableModelOutDao {
    void insert(EstimateVariableModelOut estimateVariableModelOut);

    List<EstimateVariableModelOut> select(EstimateVariableModelOut estimateVariableModelOut);

    void batchAdd(List<EstimateVariableModelOut> modelOutList);
}
