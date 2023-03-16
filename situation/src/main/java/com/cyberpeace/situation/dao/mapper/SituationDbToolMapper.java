package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationDbToolFilterReq;
import com.cyberpeace.situation.controller.res.DbToolRes;
import com.cyberpeace.situation.dao.entity.SituationDbTool;

@Mapper
public interface SituationDbToolMapper extends BaseMapper<SituationDbTool> {

	IPage<DbToolRes> selectPage(@Param("page") Page page,
			@Param("situationDbToolFilterReq") SituationDbToolFilterReq situationDbToolFilterReq);

}
