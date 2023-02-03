package com.cyberpeace.cr.redvsblue.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbMaliceProcess;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RbMaliceProcessMapper extends BaseMapper<RbMaliceProcess> {

	RbMaliceProcess getQueryset(@Param("roomId") String roomId, @Param("machineId") String machine_id, @Param("cmdList") String[] cmdList);

}
