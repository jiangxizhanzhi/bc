package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.dao.entity.DynamicScheme;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DynamicSchemeMapper extends BaseMapper<DynamicScheme> {

	/**
	 * 获取动效方案名称列表
	 * @param sceneId 场景编号
	 * @return 名称列表
	 */
	List<DynamicSchemeNameRes> getAllNamesBySceneId(String sceneId);

	/**
	 * 删除场景下动效方案数据
	 * @param sceneId 场景编号
	 */
	void deleteBySceneId(String sceneId);

	/**
	 * 根据动效名称查询动效方案
	 * @param name 动效方案名称
	 * @param sceneId 场景id
	 * @return 动效方案数据
	 */
	DynamicScheme selectByName(@Param("name") String name, @Param("sceneId") String sceneId);

	/**
	 * 根据资源编号获取动效方案数据
	 * @param resourceId 资源编号
	 * @return 动效方案数据
	 */
	DynamicScheme selectByResourceId(String resourceId);

}
