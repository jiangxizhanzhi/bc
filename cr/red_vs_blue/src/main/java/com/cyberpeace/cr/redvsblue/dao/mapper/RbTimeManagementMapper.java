package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.entity.RbTimeManagement;

@Mapper
public interface RbTimeManagementMapper extends BaseMapper<RbTimeManagement> {

	RbTimeManagement selectByRoomId(String roomId);

	List<String> selectRoomIdsByStatus(Integer status);

	List<RbTimeManagement> selectAll();

	List<RbTimeManagement> selectByStatus(@Param("status") Integer status);

	List<RbTimeManagement> selectExcludeStatus(@Param("status") Integer status);
}
