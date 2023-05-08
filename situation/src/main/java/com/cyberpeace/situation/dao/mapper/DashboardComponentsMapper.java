package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DashboardComponents;

/**
 * 大屏组件映射器
 *
 * @Author xuxz
 * @Date 11/11/2022 下午4:30
 */
@Mapper
public interface DashboardComponentsMapper extends BaseMapper<DashboardComponents> {

	/**
	 * 批量插入
	 *
	 * @param dashboardComponentsList 大屏组件列表
	 */
	void batchInsert(
			@Param("dashboardComponentsList") List<DashboardComponents> dashboardComponentsList);
}
