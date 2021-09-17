package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.AlgorithmParam;

public interface AlgorithmParamDao {

    void batchAdd(List<AlgorithmParam> params);

    void batchDelete(List<String> paramIds);


}
