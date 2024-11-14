package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;

/**
 * 测评编号Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface EvalIndexMapper extends BaseMapper<EvalIndex> {

    /**
     * 查询列表由系统id和水平
     *
     * @param systemId 系统id
     * @param level    水平
     * @return {@code List<EvalIndex> }
     */
    List<EvalIndex> selectListBySystemIdAndLevel(String systemId, Integer level);

    /**
     * 查询id列表由测评系统id列表
     *
     * @param evalSystemIds 测评系统id列表
     * @return {@code List<String> }
     */
    List<String> selectIdsByEvalSystemIds(List<String> evalSystemIds);

    /**
     * 删除批量由测评系统id
     *
     * @param evalSystemId 测评系统id
     */
    void deleteBatchByEvalSystemId(String evalSystemId);

    /**
     * 查询算法和
     *
     * @param algorithmIds 算法id列表
     * @return {@code Integer }
     */
    Integer selectAlgorithmSum(List<String> algorithmIds);

    /**
     * 存在由名称
     *
     * @param name       名称
     * @param resourceId 资源id
     * @return boolean
     */
    boolean existByName(String name, String resourceId);

    /**
     * 查询使用算法id列表
     *
     * @param algorithmIds 算法id列表
     * @return {@code List<String> }
     */
    List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




