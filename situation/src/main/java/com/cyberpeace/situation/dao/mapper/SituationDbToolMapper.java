package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationDbToolFilterReq;
import com.cyberpeace.situation.controller.res.DbToolRes;
import com.cyberpeace.situation.dao.entity.SituationDbTool;

/**
 * 数据库工具映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface SituationDbToolMapper extends BaseMapper<SituationDbTool> {

	/**
	 * 分页查询
	 *
	 * @param page                     分页条件
	 * @param situationDbToolFilterReq 查询条件
	 * @return {@code IPage<DbToolRes>}
	 */
	IPage<DbToolRes> selectPage(@Param("page") Page page,
			@Param("situationDbToolFilterReq") SituationDbToolFilterReq situationDbToolFilterReq);

}
