package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibrary;

@Mapper
public interface DynamicEffectsLibraryMapper extends BaseMapper<DynamicEffectsLibrary> {

	List<DynamicEffectsLibrary> selectByNames(@Param("names") List<String> names);

}
