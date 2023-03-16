package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationDbAccessReq;
import com.cyberpeace.situation.dao.entity.SituationDbAccess;

@Mapper
public interface SituationDbAccessMapper extends BaseMapper<SituationDbAccess> {

	IPage<SituationDbAccess> selectPage(@Param("page") Page page,
			@Param("situationDbAccessReq") SituationDbAccessReq situationDbAccessReq);

}
