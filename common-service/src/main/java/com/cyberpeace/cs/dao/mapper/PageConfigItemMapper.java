package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.cs.dao.entity.PageConfigItem;

@Mapper
public interface PageConfigItemMapper {

	List<PageConfigItem> selectByType(@Param("type") Integer type);

}
