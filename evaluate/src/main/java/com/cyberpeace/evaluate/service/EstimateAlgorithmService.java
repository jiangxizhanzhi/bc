package com.cyberpeace.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.evaluate.dao.entity.EstimateAlgorithm;
import com.cyberpeace.evaluate.service.context.EstimateAlgorithmContext;

public interface EstimateAlgorithmService {

    /**
     * 跟据类型查询所有算法数据
     * @return
     */
    List<EstimateAlgorithm> selectByType(String algorithmType);

    /**
     * 算法新增
     * @param context
     * @return
     */
    String add(EstimateAlgorithmContext context);

    void delete(List<String> algorithmIds);

    /**
     * 根据算法id查询算法
     * @param algorithmId
     * @return
     */
    EstimateAlgorithm selectById(String algorithmId);

    ListRsp<EstimateAlgorithm> selectPageByType(String type, Page pageBase);
}
