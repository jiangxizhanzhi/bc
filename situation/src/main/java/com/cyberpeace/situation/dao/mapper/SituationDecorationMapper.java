package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationDecorationPageReq;
import com.cyberpeace.situation.controller.res.SituationDecorationPageRes;
import com.cyberpeace.situation.dao.entity.SituationDecoration;

/**
 * 显示控件映射器
 *
 * @Author xuxz
 * @Date 4/1/2023 下午1:45
 */
@Mapper
public interface SituationDecorationMapper extends BaseMapper<SituationDecoration> {

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
	 * @param situationDecorationPageReq
	 * @return
	 */
	IPage<SituationDecorationPageRes> selectPage(@Param("page") Page page,
			@Param("situationDecorationPageReq") SituationDecorationPageReq situationDecorationPageReq);

}
