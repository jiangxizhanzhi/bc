package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.AlgorithmParamLink;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlgorithmParamLinkMapper {

    void insert(AlgorithmParamLink algorithmParamLink);

    void batchAdd(List<AlgorithmParamLink> algorithmParamLinks);

    List<AlgorithmParamLink> selectByAlgorithm(String algorithmId);

    void deleteByAlgorithm(String algorithmId);
}
