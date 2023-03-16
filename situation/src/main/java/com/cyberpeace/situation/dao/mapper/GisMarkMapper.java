package com.cyberpeace.situation.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.GisMark;
import com.cyberpeace.situation.service.context.GisMarkContext;

@Mapper
public interface GisMarkMapper extends BaseMapper<GisMark> {

	List<GisMarkContext> getGisMarkList(String dashboardId, Date currentTime);

	List<String> getMarkIdsByDashboardIds(@Param("dashboardIds") List<String> dashboardIds);

	Boolean checkMark(@Param("dashboardId") String dashboardId, @Param("markId") String markId);

	void insertBatch(@Param("marks") List<GisMark> marks);

}
