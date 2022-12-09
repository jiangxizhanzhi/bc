package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;

@Mapper
public interface DashboardAttributeMapper extends BaseMapper<DashboardAttribute> {

	DashboardAttribute getById(@Param("dashboardId") String dashboardId);

	DashboardAttribute getByTaskId(@Param("taskId") String taskId);
}
