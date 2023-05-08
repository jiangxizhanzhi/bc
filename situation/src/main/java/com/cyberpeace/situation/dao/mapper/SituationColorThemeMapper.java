package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.ColorThemePageReq;
import com.cyberpeace.situation.dao.entity.ColorTheme;

/**
 * 图表主题映射器
 *
 * @Author xuxz
 * @Date 8/12/2022 上午9:08
 */
@Mapper
public interface SituationColorThemeMapper extends BaseMapper<ColorTheme> {

	/**
	 * 分页查询
	 *
	 * @param page              分页
	 * @param colorThemePageReq 查询条件
	 * @return {@code IPage<ColorTheme>}
	 */
	IPage<ColorTheme> selectPage(@Param("page") Page page,
			@Param("colorThemePageReq") ColorThemePageReq colorThemePageReq);
}
