package com.cyberpeace.cr.evaluation.dao.mapper;

import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandModelReq;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandLog;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandParameters;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandTemplates;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EvalDeviceMapper {

    List<CrRbTargetCommandTemplates> getDeviceCommandModels(@Param("param") DeviceCommandModelReq req);

    List<CrRbTargetCommandParameters> getDeviceCommandParameters(@Param("id") String id);

    void insert(@Param("param")CrRbTargetCommandLog log);
}




