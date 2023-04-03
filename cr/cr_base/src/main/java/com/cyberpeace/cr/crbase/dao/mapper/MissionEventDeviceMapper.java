package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.MissionEventDevice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MissionEventDeviceMapper extends BaseMapper<MissionEventDevice> {

	List<String> getDeviceIdsByMissionId(String missionId);
}
