package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.dao.entity.DynamicScheme;

/**
 * 动态方案映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
@Repository
public interface DynamicSchemeMapper extends BaseMapper<DynamicScheme> {

	/**
	 * 根据场景ID获取所有方案名称
	 *
	 * @param sceneId 场景ID
	 * @return {@code List<DynamicSchemeNameRes>}
	 */
	List<DynamicSchemeNameRes> getAllNamesBySceneId(String sceneId);

	/**
	 * 通过场景ID删除方案
	 *
	 * @param sceneId 场景ID
	 */
	void deleteBySceneId(String sceneId);

	/**
	 * 通过名称获取方案
	 *
	 * @param name    名称
	 * @param sceneId 场景ID
	 * @return {@code DynamicScheme}
	 */
	DynamicScheme selectByName(@Param("name") String name, @Param("sceneId") String sceneId);

	/**
	 * 通过资源ID获取方案
	 *
	 * @param resourceId 资源id
	 * @return {@code DynamicScheme}
	 */
	DynamicScheme selectByResourceId(String resourceId);

}
