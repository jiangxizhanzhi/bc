package com.cyberpeace.bs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.service.bo.simulator.SceneIdName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户行为模拟仪表Mapper
 *
 * @author SN
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface UserSimulatorMapper extends BaseMapper<UserSimulator> {

    /**
     * 查询所有
     *
     * @return {@code List<UserSimulator>}
     */
    List<UserSimulator> selectAll();

    /**
     * 获取全部数量
     *
     * @return {@code Integer}
     */
    Integer countAll();

    /**
     * 按状态统计数量
     *
     * @param statusCode 状态码
     * @return {@code Integer}
     */
    Integer countByStatus(@Param("statusCode") Integer statusCode);

    /**
     * 按状态批量查询
     *
     * @param code 状态枚举值
     * @return {@code List<UserSimulator>}
     */
    List<UserSimulator> selectBatchByStatus(@Param("code") Integer code);

    /**
     * 查询分页
     *
     * @param page    分页器
     * @param sceneId 场景id
     * @return {@code IPage<UserSimulator>}
     */
    IPage<UserSimulator> page(@Param("page") Page page, @Param("sceneId") String sceneId);

    /**
     * 通过场景id查询仪表id列表
     *
     * @param sceneId 场景id
     * @return {@code List<String>}
     */
    List<String> getIdsBySceneId(@Param("sceneId") String sceneId);

    /**
     * 通过场景id删除
     *
     * @param sceneId 场景id
     */
    void deleteBySceneId(@Param("sceneId") String sceneId);

    /**
     * 获取场景列表
     *
     * @return {@code List<SceneIdName>}
     */
    List<SceneIdName> getSceneList();

    /**
     * 通过场景id列表获取仪表id列表
     *
     * @param sceneIdList 场景id列表
     * @return {@code List<String>}
     */
    List<String> getIdsBySceneIdList(@Param("sceneIdList") List<String> sceneIdList);

    /**
     * 通过场景id列表删除仪表
     *
     * @param sceneIdList 场景id列表
     */
    void deleteBySceneIdList(@Param("sceneIdList") List<String> sceneIdList);

    /**
     * 通过节点id查询仪表
     *
     * @param nodeId 节点id
     * @return {@code UserSimulator}
     */
    UserSimulator selectByNodeId(@Param("nodeId") String nodeId);

    /**
     * 通过场景id批量查询仪表
     *
     * @param sceneId 场景id
     * @return {@code List<UserSimulator>}
     */
    List<UserSimulator> selectBatchBySceneId(@Param("sceneId") String sceneId);

    /**
     * 通过设备id查询仪表id列表
     *
     * @param deviceIds 设备id列表
     * @return {@code List<String>}
     */
    List<String> getIdsByDeviceIds(@Param("deviceIds") List<String> deviceIds);
}
