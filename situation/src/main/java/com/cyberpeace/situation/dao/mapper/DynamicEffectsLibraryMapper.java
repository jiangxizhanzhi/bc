package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibrary;

/**
 * 动效映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface DynamicEffectsLibraryMapper extends BaseMapper<DynamicEffectsLibrary> {

	/**
	 * 通过名字获取动效
	 *
	 * @param names 名字
	 * @return {@code List<DynamicEffectsLibrary>}
	 */
	List<DynamicEffectsLibrary> selectByNames(@Param("names") List<String> names);

}
