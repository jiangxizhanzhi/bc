package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateVariableModelOut;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EstimateVariableModelOutMapper {

	void insert(EstimateVariableModelOut variableModelOut);

	void update(EstimateVariableModelOut variableModelOut);

	List<EstimateVariableModelOut> select(EstimateVariableModelOut estimateVariableModelOut);

	void batchAdd(List<EstimateVariableModelOut> modelOutList);
}
