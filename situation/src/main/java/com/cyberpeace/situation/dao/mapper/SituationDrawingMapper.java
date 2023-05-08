package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationDrawingPageReq;
import com.cyberpeace.situation.controller.res.SituationDrawingPageRes;
import com.cyberpeace.situation.dao.entity.SituationDrawing;

/**
 * 样式组件映射器
 *
 * @Author xuxz
 * @Date 4/1/2023 下午1:45
 */
@Mapper
public interface SituationDrawingMapper extends BaseMapper<SituationDrawing> {

	/**
	 * 通过名称获取
	 *
	 * @param name
	 * @return
	 */
	String selectByName(@Param("name") String name);

	/**
	 * 分页查询
	 *
	 * @param page
	 * @param situationDrawingPageReq
	 * @return
	 */
	IPage<SituationDrawingPageRes> selectPage(@Param("page") Page page,
			@Param("situationDrawingPageReq") SituationDrawingPageReq situationDrawingPageReq);

}
