package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationPlotPixelPageReq;
import com.cyberpeace.situation.dao.entity.SituationPlotPixel;

/**
 * 标绘图元映射器
 *
 * @Author xuxz
 * @Date 17/12/2022 下午5:11
 */
@Mapper
public interface SituationPlotPixelMapper extends BaseMapper<SituationPlotPixel> {

	/**
	 * 分页查询
	 * @param page
	 * @param situationPlotPixelPageReq
	 * @return
	 */
	IPage<SituationPlotPixel> selectPage(@Param("page") Page page,
			@Param("situationPlotPixelPageReq") SituationPlotPixelPageReq situationPlotPixelPageReq);
}
