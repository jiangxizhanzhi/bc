package com.cyberpeace.bs.dao.mapper;

import com.cyberpeace.bs.service.bo.device.DevicePortId;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface DeviceOccupationMapper{
    Set<String> checkOccupation(@Param("deviceIds") Collection<String> deviceIds);

    List<DevicePortId> getPortByIds(@Param("deviceIds") Collection<String> deviceIds);
}
