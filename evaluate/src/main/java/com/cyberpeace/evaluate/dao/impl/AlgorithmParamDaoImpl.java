package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.AlgorithmParamDao;
import com.cyberpeace.evaluate.dao.entity.AlgorithmParam;
import com.cyberpeace.evaluate.dao.mapper.AlgorithmParamMapper;

@Repository
public class AlgorithmParamDaoImpl implements AlgorithmParamDao {

    @Autowired
    private AlgorithmParamMapper algorithmParamMapper;

    @Override
    public void batchAdd(List<AlgorithmParam> params) {
        algorithmParamMapper.batchAdd(params);
    }

    @Override
    public void batchDelete(List<String> paramIds) {
        algorithmParamMapper.batchDelete(paramIds);
    }
}
