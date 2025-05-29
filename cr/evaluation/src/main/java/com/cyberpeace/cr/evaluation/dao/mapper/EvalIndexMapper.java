package com.cyberpeace.cr.evaluation.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评估指数Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalIndexMapper` 的接口，该接口继承自 `BaseMapper<EvalIndex>`，并且使用了 `@Mapper` 注解。`BaseMapper` 是 MyBatis-Plus 提供的一个基础接口，提供了许多常用的 CRUD（创建、读取、更新、删除）操作。通过继承这个接口，`EvalIndexMapper` 可以直接使用这些基本操作，而无需手动编写 SQL。 
 * ### 接口描述 
 * `EvalIndexMapper` 接口主要用于管理 `EvalIndex` 实体对象的操作。`EvalIndex` 是一个实体类，表示某种评估指标或指数。 
 * ### 主要方法 
 * 1. **`selectListBySystemIdAndLevel`**: 
 * - **功能**: 根据系统ID和水平查询评估指数列表。 
 * - **参数**: 
 * - `systemId`: 系统ID 
 * - `level`: 水平 
 * - **返回值**: `List<EvalIndex>` 
 * 2. **`selectIdsByEvalSystemIds`**: 
 * - **功能**: 根据评估系统ID列表查询对应的ID列表。 
 * - **参数**: `evalSystemIds` - 评估系统ID列表 
 * - **返回值**: `List<String>` 
 * 3. **`deleteBatchByEvalSystemId`**: 
 * - **功能**: 根据评估系统ID批量删除记录。 
 * - **参数**: `evalSystemId` - 评估系统ID 
 * 4. **`selectAlgorithmSum`**: 
 * - **功能**: 根据算法ID列表查询某种汇总信息（可能是算法数量或其他统计信息）。 
 * - **参数**: `algorithmIds` - 算法ID列表 
 * - **返回值**: `Integer` 
 * 5. **`existByName`**: 
 * - **功能**: 根据名称和资源ID检查是否存在记录。 
 * - **参数**: 
 * - `name`: 名称 
 * - `resourceId`: 资源ID 
 * - **返回值**: `boolean` 
 * 6. **`selectUsedAlgorithmIds`**: 
 * - **功能**: 查询使用的算法ID列表。 
 * - **参数**: `algorithmIds` - 算法ID列表 
 * - **返回值**: `List<String>` 
 * ### 总结 
 * `EvalIndexMapper` 接口定义了一组用于管理 `EvalIndex` 实体的方法，包括根据不同条件查询数据、批量删除数据以及检查某些条件的存在性等。这些方法可以简化数据库操作，并提高代码的可维护性和可读性。 
**/
@Mapper
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
     * 查询id列表由评估系统id列表
     *
     * @param evalSystemIds 评估系统id列表
     * @return {@code List<String> }
     */
    List<String> selectIdsByEvalSystemIds(List<String> evalSystemIds);

    /**
     * 删除批量由评估系统id
     *
     * @param evalSystemId 评估系统id
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





