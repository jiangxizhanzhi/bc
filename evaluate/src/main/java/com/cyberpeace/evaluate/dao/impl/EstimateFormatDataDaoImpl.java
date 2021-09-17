package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateFormatDataDao;
import com.cyberpeace.evaluate.dao.entity.EstimateFormatData;
import com.cyberpeace.evaluate.dao.mapper.EstimateFormatDataMapper;

@Repository
public class EstimateFormatDataDaoImpl implements EstimateFormatDataDao {

    @Autowired
    private EstimateFormatDataMapper estimateFormatDataMapper;

    @Override
    public List<EstimateFormatData> select(EstimateFormatData estimateFormatData, Integer length) {
        List<EstimateFormatData> formatDatalist = estimateFormatDataMapper.select(estimateFormatData, length);
        return formatDatalist;
    }

    @Override
    public void insert(EstimateFormatData estimateFormatData) {
        estimateFormatDataMapper.insert(estimateFormatData);
    }

    @Override
    public void batchAdd(List<EstimateFormatData> formatDataList) {
        if(formatDataList.isEmpty()){
            return;
        }
        estimateFormatDataMapper.batchAdd(formatDataList);
    }
}
