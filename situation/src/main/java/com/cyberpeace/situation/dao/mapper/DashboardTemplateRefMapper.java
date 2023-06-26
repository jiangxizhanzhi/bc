package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DashboardTemplateRef;

/**
 * 大屏模板副本映射器
 *
 * @Author xuxz
 * @Date 11/11/2022 下午4:22
 */
@Mapper
public interface DashboardTemplateRefMapper extends BaseMapper<DashboardTemplateRef> {

	/**
	 * 获取未使用的模板数据
	 * @return
	 */
	List<String> selectAllId();

	/**
	 * 获取源模板ID
	 *
	 * @param resourceId 模板副本ID
	 * @return {@code String}
	 */
	String selectOriginTemplateId(@Param("resourceId") String resourceId);

	/**
	 * 通过起源模板id查找
	 *
	 * @param originTemplateId 模板id
	 * @return {@link DashboardTemplateRef}
	 */
	DashboardTemplateRef selectByOriginTemplateId(String originTemplateId);

}
