package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.EstimateFormatData;

@Mapper
public interface EstimateFormatDataMapper {

	void insert(EstimateFormatData estimateFormatData);

	List<EstimateFormatData> select(@Param("formateData") EstimateFormatData estimateFormatData,
			@Param("dateLength") Integer length);

	void batchAdd(List<EstimateFormatData> formatDataList);
}
