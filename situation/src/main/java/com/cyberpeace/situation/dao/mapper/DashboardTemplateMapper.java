package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.DashboardTemplatePageReq;
import com.cyberpeace.situation.controller.res.DashboardTemplatePageRes;
import com.cyberpeace.situation.dao.entity.DashboardTemplate;

/**
 * 大屏模板映射器
 *
 * @Author xuxz
 * @Date 11/11/2022 下午4:22
 */
@Mapper
public interface DashboardTemplateMapper extends BaseMapper<DashboardTemplate> {

	/**
	 * 分页查询
	 *
	 * @param page                     分页信息
	 * @param dashboardTemplatePageReq 分页过滤条件
	 * @return {@code IPage<DashboardTemplatePageRes>}
	 */
	IPage<DashboardTemplatePageRes> selectPage(@Param("page") Page page,
			@Param("dashboardTemplatePageReq") DashboardTemplatePageReq dashboardTemplatePageReq);

	/**
	 * 通过名称查询
	 *
	 * @param name 名称
	 * @return {@code String}
	 */
	String selectByName(@Param("name") String name);

	/**
	 * 通过类型查询
	 *
	 * @param type 类型
	 * @return {@code List<String>}
	 */
	List<String> selectByType(@Param("type") String type);

	/**
	 * 通过层级查询
	 *
	 * @param level 层级
	 * @return {@code String}
	 */
	String selectByLevel(@Param("level") Integer level);

	/**
	 * 获取所有存在定制模板的大屏类型
	 *
	 * @return {@code List<String>}
	 */
	List<String> selectCustomTypes();
}
