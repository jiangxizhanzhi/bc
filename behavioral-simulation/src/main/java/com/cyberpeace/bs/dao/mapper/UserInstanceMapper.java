package com.cyberpeace.bs.dao.mapper;

import com.cyberpeace.bs.dao.entity.UserInstance;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * 用户实例Mapper
 *
 * @author SN
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface UserInstanceMapper {

    /**
     * 批量插入用户实例
     *
     * @param userInstanceAll 用户实例
     */
    void insertUserInstanceBatch(@Param("userInstanceAll") List<UserInstance> userInstanceAll);

    /**
     * 通过仪表id批量查询用户实例
     *
     * @param simulatorId 仪表id
     * @return {@code List<UserInstance>}
     */
    List<UserInstance> getBatchBySimulatorId(@Param("simulatorId") String simulatorId);

    /**
     * 通过仪表id列表删除用户实例
     *
     * @param simulatorIds 仪表id列表
     */
    void deleteBySimulatorIds(@Param("simulatorIds") Collection<String> simulatorIds);

    /**
     * 通过仪表id删除用户实例
     *
     * @param simulatorId 仪表id
     */
    void deleteBySimulatorId(@Param("simulatorId") String simulatorId);

    /**
     * 通过仪表id列表批量获取用户实例
     *
     * @param simulatorIds 仪表id
     * @return {@code List<UserInstance>}
     */
    List<UserInstance>
    getBatchBySimulatorIds(@Param("simulatorIds") Collection<String> simulatorIds);
}
