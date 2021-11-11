package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;

@Mapper
@Repository
public interface CrTaskSubmitMapper extends BaseMapper<CrTaskSubmit> {

	List<CrTaskSubmit> selectByRoomId(String roomId);

	CrTaskSubmit selectLastByTaskId(String taskId);
}
