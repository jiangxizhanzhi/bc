package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.situation.controller.req.DataSourceRecordPageReq;
import com.cyberpeace.situation.controller.res.DataSourceRecordRes;
import com.cyberpeace.situation.dao.entity.DataSourceRecord;

/**
 * 数据源记录映射器
 *
 * @Author xuxz
 * @Date 3/11/2022 下午4:45
 */
@Mapper
public interface DataSourceRecordMapper extends BaseMapper<DataSourceRecord> {

	/**
	 * 分页查询
	 *
	 * @param page                    分页信息
	 * @param authContext             身份验证信息
	 * @param dataSourceRecordPageReq 分页过滤条件
	 * @return {@code IPage<DataSourceRecordRes>}
	 */
	IPage<DataSourceRecordRes> selectPage(@Param("page") Page page,
			@Param("authContext") AuthContext authContext,
			@Param("dataSourceRecordPageReq") DataSourceRecordPageReq dataSourceRecordPageReq);

	/**
	 * 通过资源ID查询
	 *
	 * @param resourceId 资源ID
	 * @return {@code DataSourceRecord}
	 */
	DataSourceRecord selectById(@Param("resourceId") String resourceId);
}
