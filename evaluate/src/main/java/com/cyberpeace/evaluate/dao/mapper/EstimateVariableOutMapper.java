package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateVariableOut;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EstimateVariableOutMapper {

	void insert(EstimateVariableOut estimateVariableOut);

	List<EstimateVariableOut> select(EstimateVariableOut input);
}
