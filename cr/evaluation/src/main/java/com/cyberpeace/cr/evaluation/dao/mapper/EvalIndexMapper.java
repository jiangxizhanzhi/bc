package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;

/**
 * 评估指数Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
public interface EvalIndexMapper extends BaseMapper<EvalIndex> {

    /**
     * 查询列表由系统id和水平
     *
     * @param systemId 系统id
     * @param level    水平
     * @return {@code List<EvalIndex> }
     * @throws
     * @see String
     * @see Integer
     * @see List<EvalIndex>
     */
    List<EvalIndex> selectListBySystemIdAndLevel(String systemId, Integer level);

    /**
     * 查询id列表由评估系统id列表
     *
     * @param evalSystemIds 评估系统id列表
     * @return {@code List<String> }
     * @throws
     * @see List<String>
     * @see List<String>
     */
    List<String> selectIdsByEvalSystemIds(List<String> evalSystemIds);

    /**
     * 删除批量由评估系统id
     *
     * @param evalSystemId 评估系统id
     * @throws
     * @see String
     */
    void deleteBatchByEvalSystemId(String evalSystemId);

    /**
     * 查询算法和
     *
     * @param algorithmIds 算法id列表
     * @return {@code Integer }
     * @throws
     * @see List<String>
     * @see Integer
     */
    Integer selectAlgorithmSum(List<String> algorithmIds);

    /**
     * 存在由名称
     *
     * @param name       名称
     * @param resourceId 资源id
     * @return boolean
     * @throws
     * @see String
     * @see String
     * @see boolean
     */
    boolean existByName(String name, String resourceId);

    /**
     * 查询使用算法id列表
     *
     * @param algorithmIds 算法id列表
     * @return {@code List<String> }
     * @throws
     * @see List<String>
     * @see List<String>
     */
    List<String> selectUsedAlgorithmIds(List<String> algorithmIds);
}




