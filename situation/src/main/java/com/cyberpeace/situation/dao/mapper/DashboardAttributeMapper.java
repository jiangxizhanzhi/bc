package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;

/**
 * 大屏属性映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface DashboardAttributeMapper extends BaseMapper<DashboardAttribute> {

	/**
	 * 通过大屏ID获取属性
	 *
	 * @param dashboardId 大屏ID
	 * @return {@code DashboardAttribute}
	 */
	DashboardAttribute getByDashboardId(@Param("dashboardId") String dashboardId);

	/**
	 * 通过资源ID获取属性
	 *
	 * @param resourceId 资源id
	 * @return {@code DashboardAttribute}
	 */
	DashboardAttribute getByResourceId(@Param("resourceId") String resourceId);

	/**
	 * 通过任务id获取属性
	 *
	 * @param taskId 任务id
	 * @return {@code DashboardAttribute}
	 */
	DashboardAttribute getByTaskId(@Param("taskId") String taskId);
}
