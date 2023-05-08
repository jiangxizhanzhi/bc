package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationDbAccessReq;
import com.cyberpeace.situation.dao.entity.SituationDbAccess;

/**
 * 情况数据库操作记录映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface SituationDbAccessMapper extends BaseMapper<SituationDbAccess> {

	/**
	 * 分页查询
	 *
	 * @param page                 分页条件
	 * @param situationDbAccessReq 查询条件
	 * @return {@code IPage<SituationDbAccess>}
	 */
	IPage<SituationDbAccess> selectPage(@Param("page") Page page,
			@Param("situationDbAccessReq") SituationDbAccessReq situationDbAccessReq);

}
