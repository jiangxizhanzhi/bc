package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DynamicEffect;

/**
 * 动效映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface DynamicEffectsMapper extends BaseMapper<DynamicEffect> {

	/**
	 * 通过名字获取动效
	 *
	 * @param names 名字
	 * @return {@code List<DynamicEffect>}
	 */
	List<DynamicEffect> selectByNames(@Param("names") List<String> names);

	/**
	 * 校验动效名称是否已存在
	 *
	 * @param resourceId 资源id
	 * @param name       名称
	 * @return {@code Boolean}
	 */
	Boolean checkName(@Param("resourceId") String resourceId, @Param("name") String name);

}
