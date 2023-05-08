package com.cyberpeace.situation.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.GisMark;
import com.cyberpeace.situation.service.context.GisMarkContext;

/**
 * GIS标绘映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface GisMarkMapper extends BaseMapper<GisMark> {

	/**
	 * 获取大屏GIS标绘列表
	 *
	 * @param dashboardId 大屏id
	 * @param currentTime 当前时间
	 * @return {@code List<GisMarkContext>}
	 */
	List<GisMarkContext> getGisMarkList(String dashboardId, Date currentTime);

	/**
	 * 通过大屏id获取标绘id列表
	 *
	 * @param dashboardIds 大屏id列表
	 * @return {@code List<String>}
	 */
	List<String> getMarkIdsByDashboardIds(@Param("dashboardIds") List<String> dashboardIds);

	/**
	 * 校验标绘是否存在
	 *
	 * @param dashboardId 大屏id
	 * @param markId      标绘id
	 * @return {@code Boolean}
	 */
	Boolean checkMark(@Param("dashboardId") String dashboardId, @Param("markId") String markId);

	/**
	 * 批量插入
	 *
	 * @param marks 标绘数据
	 */
	void insertBatch(@Param("marks") List<GisMark> marks);

}
