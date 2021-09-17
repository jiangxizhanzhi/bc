package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.evaluate.dao.EstimateIndexDao;
import com.cyberpeace.evaluate.dao.entity.EstimateIndex;
import com.cyberpeace.evaluate.dao.mapper.EstimateIndexMapper;

@Service
public class EstimateIndexDaoImpl implements EstimateIndexDao {

    @Autowired
    private EstimateIndexMapper estimateIndexMapper;

    @Override
    public void insert(EstimateIndex estimateIndex) {
        estimateIndexMapper.insert(estimateIndex);
    }

    @Override
    public List<EstimateIndex> selectByScheme(String schemeId) {
        return estimateIndexMapper.selectByScheme(schemeId);
    }

    @Override
    public void batchDelete(List<String> indexIds) {
        estimateIndexMapper.batchDelete(indexIds);
    }

    @Override
    public void deleteByScheme(String schemeId) {
        estimateIndexMapper.deleteByScheme(schemeId);
    }

    @Override
    public EstimateIndex selectById(String indexId) {
        return estimateIndexMapper.selectById(indexId);
    }
}
