package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.situation.controller.req.DataSourceRecordPageReq;
import com.cyberpeace.situation.controller.res.DataSourceRecordRes;
import com.cyberpeace.situation.dao.entity.DataSourceRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author xuxz
 * @Date 3/11/2022 下午4:45
 */
@Mapper
public interface DataSourceRecordMapper extends BaseMapper<DataSourceRecord> {

    IPage<DataSourceRecordRes> selectPage(@Param("page") Page page,
                                          @Param("authContext") AuthContext authContext,
                                          @Param("dataSourceRecordPageReq") DataSourceRecordPageReq dataSourceRecordPageReq);

    DataSourceRecord selectById(@Param("resourceId")String resourceId);
}
