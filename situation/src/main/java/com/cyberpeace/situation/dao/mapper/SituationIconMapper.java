package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationIconPageReq;
import com.cyberpeace.situation.controller.res.SituationIconRes;
import com.cyberpeace.situation.dao.entity.SituationIcon;

/**
 * 图标映射器
 *
 * @Author xuxz
 * @Date 15/12/2022 上午11:29
 */
@Mapper
public interface SituationIconMapper extends BaseMapper<SituationIcon> {

	/**
	 * 通过名称获取
	 * @param name
	 * @return
	 */
	String selectByName(@Param("name") String name);

	/**
	 * 分页查询
	 * @param page
	 * @param situationIconPageReq
	 * @return
	 */
	IPage<SituationIconRes> selectPage(@Param("page") Page page,
			@Param("situationIconPageReq") SituationIconPageReq situationIconPageReq);
}
