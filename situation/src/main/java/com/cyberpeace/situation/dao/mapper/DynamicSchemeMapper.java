package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.dao.entity.DynamicScheme;

/**
 * 动态方案Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface DynamicSchemeMapper extends BaseMapper<DynamicScheme> {

	/**
	 * 获取全部名字由场景id
	 *
	 * @param sceneId 场景id
	 * @return {@code List<DynamicSchemeNameRes> }
	 * @throws
	 * @see String
	 * @see List<DynamicSchemeNameRes>
	 */
	List<DynamicSchemeNameRes> getAllNamesBySceneId(String sceneId);

	/**
	 * 删除由场景id
	 *
	 * @param sceneId 场景id
	 * @throws
	 * @see String
	 */
	void deleteBySceneId(String sceneId);

	/**
	 * 查询由名称
	 *
	 * @param name    名称
	 * @param sceneId 场景id
	 * @return {@code DynamicScheme }
	 * @throws
	 * @see String
	 * @see String
	 * @see DynamicScheme
	 */
	DynamicScheme selectByName(@Param("name") String name, @Param("sceneId") String sceneId);

	/**
	 * 查询由资源id
	 *
	 * @param resourceId 资源id
	 * @return {@code DynamicScheme }
	 * @throws
	 * @see String
	 * @see DynamicScheme
	 */
	DynamicScheme selectByResourceId(String resourceId);

}
