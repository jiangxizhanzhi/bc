package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.AlgorithmParamLinkDao;
import com.cyberpeace.evaluate.dao.entity.AlgorithmParamLink;
import com.cyberpeace.evaluate.dao.mapper.AlgorithmParamLinkMapper;

@Repository
public class AlgorithmParamLinkDaoImpl implements AlgorithmParamLinkDao {

    @Autowired
    private AlgorithmParamLinkMapper algorithmParamLinkMapper;

    @Override
    public void batchAdd(List<AlgorithmParamLink> paramLinks) {
        algorithmParamLinkMapper.batchAdd(paramLinks);
    }

    @Override
    public List<AlgorithmParamLink> selectByAlgorithm(String algorithmId) {
        return algorithmParamLinkMapper.selectByAlgorithm(algorithmId);
    }

    @Override
    public void deleteByAlgorithm(String algorithmId) {
        algorithmParamLinkMapper.deleteByAlgorithm(algorithmId);
    }
}
