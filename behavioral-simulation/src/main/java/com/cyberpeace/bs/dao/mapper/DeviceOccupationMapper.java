package com.cyberpeace.bs.dao.mapper;

import com.cyberpeace.bs.dao.entity.DeviceOccupation;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 实体设备占用情况Mapper
 *
 * @author SN
 * @date 2023/05/06
 */
@Mapper
@Repository
public interface DeviceOccupationMapper {

    /**
     * 检查占用情况
     *
     * @param deviceIds 设备id
     * @return {@code Set<String>}
     */
    Set<String> checkOccupation(@Param("deviceIds") Collection<String> deviceIds);

    /**
     * 通过id批量查询占用情况
     *
     * @param deviceIds 设备id
     * @return {@code List<DeviceOccupation>}
     */
    List<DeviceOccupation> batchGetByIds(@Param("deviceIds") Collection<String> deviceIds);

    /**
     * 占用设备
     *
     * @param selectedIds 选中的设备id
     * @param sceneId     场景id
     * @param date        日期
     */
    void occupy(@Param("selectedIds") Collection<String> selectedIds,
                @Param("sceneId") String sceneId, @Param("date") Date date);

    /**
     * 通过场景id释放对设备的占用
     *
     * @param sceneId 场景id
     */
    void releaseBySceneId(@Param("sceneId") String sceneId);

    /**
     * 通过场景id列表释放对设备的占用
     *
     * @param sceneIdList 场景id列表
     */
    void releaseBySceneIdList(@Param("sceneIdList") List<String> sceneIdList);
}
