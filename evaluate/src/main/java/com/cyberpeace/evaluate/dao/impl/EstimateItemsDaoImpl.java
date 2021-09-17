package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateItemsDao;
import com.cyberpeace.evaluate.dao.entity.EstimateItems;
import com.cyberpeace.evaluate.dao.mapper.EstimateItemsMapper;

@Repository
public class EstimateItemsDaoImpl implements EstimateItemsDao {

    @Autowired
    private EstimateItemsMapper estimateItemsMapper;

    @Override
    public void add(EstimateItems estimateItems) {
        estimateItemsMapper.insert(estimateItems);
    }

    @Override
    public List<EstimateItems> selectByScheme(String schemeId) {
        return estimateItemsMapper.selectByScheme(schemeId);
    }

    @Override
    public void batchAdd(List<EstimateItems> estimateItems) {
        estimateItemsMapper.batchAdd(estimateItems);
    }

    @Override
    public void batchDelete(List<String> estimateItemIds) {
        estimateItemsMapper.batchDelete(estimateItemIds);
    }

    @Override
    public void deleteByScheme(String schemeId) {
        estimateItemsMapper.deleteByScheme(schemeId);
    }

    @Override
    public EstimateItems selectById(String itemId) {
        return estimateItemsMapper.selectById(itemId);
    }
}
