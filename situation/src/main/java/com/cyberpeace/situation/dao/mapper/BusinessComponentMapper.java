package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.BusinessComponentPageReq;
import com.cyberpeace.situation.dao.entity.BusinessComponent;

/**
 * 业务组件映射器
 *
 * @Author xuxz
 * @Date 11/11/2022 下午4:22
 */
@Mapper
public interface BusinessComponentMapper extends BaseMapper<BusinessComponent> {

	/**选择页面
	 * 分页查询
	 *
	 * @param page                     分页信息
	 *
	 @param businessComponentPageReq 业务组件页面请求
	  * @return {@link IPage}<{@link BusinessComponent}>
	 */
	IPage<BusinessComponent> selectPage(@Param("page") Page page,
			@Param("businessComponentPageReq") BusinessComponentPageReq businessComponentPageReq);

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

}
