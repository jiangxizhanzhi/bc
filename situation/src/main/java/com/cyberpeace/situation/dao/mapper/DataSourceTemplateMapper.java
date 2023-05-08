package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.situation.dao.entity.DataSourceTemplate;

/**
 * 数据源模板映射器
 *
 * @Author xuxz
 * @Date 3/11/2022 上午10:18
 */
@Mapper
public interface DataSourceTemplateMapper {

	/**
	 * 获取数据源类型列表
	 * @return
	 */
	List<String> getDataSourceType();

	/**
	 * 根据类型回去数据源
	 * @param type
	 * @return
	 */
	DataSourceTemplate selectByType(@Param("type") String type);
}
