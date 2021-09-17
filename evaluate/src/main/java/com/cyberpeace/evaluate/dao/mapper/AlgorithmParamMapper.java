package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.AlgorithmParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlgorithmParamMapper {

    void insert(AlgorithmParam algorithmParam);

    void batchAdd(List<AlgorithmParam> algorithmParams);

    void batchDelete(List<String> ids);
}
