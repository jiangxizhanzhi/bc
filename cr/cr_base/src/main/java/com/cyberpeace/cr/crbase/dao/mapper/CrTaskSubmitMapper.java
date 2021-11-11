package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrTaskSubmitMapper extends BaseMapper<CrTaskSubmit> {

    List<CrTaskSubmit> selectByRoomId(String roomId);

    CrTaskSubmit selectLastByTaskId(String taskId);
}
