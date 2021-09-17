package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateFormatData;

public interface EstimateFormatDataDao {

	List<EstimateFormatData> select(EstimateFormatData estimateFormatData, Integer length);

	void insert(EstimateFormatData estimateFormatData);

	void batchAdd(List<EstimateFormatData> formatDataList);
}
