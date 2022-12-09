package com.cyberpeace.bs.dao.mapper;

import com.cyberpeace.bs.dao.entity.DeviceOccupation;
import com.cyberpeace.bs.service.bo.device.DevicePortId;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface DeviceOccupationMapper {
    Set<String> checkOccupation(@Param("deviceIds") Collection<String> deviceIds);

    List<DeviceOccupation> batchGetByIds(@Param("deviceIds") Collection<String> deviceIds);

    void occupy(@Param("selectedIds") Collection<String> selectedIds, @Param("sceneId") String sceneId,
                @Param("date") Date date);

    void releaseBySceneId(@Param("sceneId") String sceneId);

    void releaseBySceneIdList(@Param("sceneIdList") List<String> sceneIdList);
}
