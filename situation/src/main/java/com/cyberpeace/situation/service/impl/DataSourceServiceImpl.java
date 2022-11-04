package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.situation.controller.req.DataSourceRecordDelReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordPageReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordReq;
import com.cyberpeace.situation.controller.res.DataResourceRecordInfoRes;
import com.cyberpeace.situation.controller.res.DataSourceRecordRes;
import com.cyberpeace.situation.dao.entity.DataSourceRecord;
import com.cyberpeace.situation.dao.entity.DataSourceTemplate;
import com.cyberpeace.situation.dao.mapper.DataSourceRecordMapper;
import com.cyberpeace.situation.dao.mapper.DataSourceTemplateMapper;
import com.cyberpeace.situation.service.DataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.rmi.NotBoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuxz
 * @Date 28/10/2022 下午2:07
 */
@Service
@Slf4j
public class DataSourceServiceImpl extends ServiceImpl<DataSourceRecordMapper, DataSourceRecord> implements DataSourceService {

    @Autowired
    private DataSourceTemplateMapper dataSourceTemplateMapper;

    @Autowired
    private DataSourceRecordMapper dataSourceRecordMapper;

    @Override
    public String testDBConnection(DataSourceRecordReq dataSourceRecordReq) {
        String type = dataSourceRecordReq.getType();
        if (StringUtils.isEmpty(type)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        DataSourceTemplate dataSourceTemplate = dataSourceTemplateMapper.selectByType(type);
        if (dataSourceTemplate == null) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        Connection conn = null;
        try {
            Class.forName(dataSourceTemplate.getDataSource());
            String url = String.format(dataSourceTemplate.getConnectUrl(), dataSourceRecordReq.getConnectAddress(), dataSourceRecordReq.getPort());
            conn = DriverManager.getConnection(url, dataSourceRecordReq.getUserName(), dataSourceRecordReq.getPassword());
            conn.setAutoCommit(false);
            return conn.toString();
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.CONNECT_DATABASE_FAILED);
        } finally {
            closeDB(conn, null, null);
        }
    }

    @Override
    public String testDBConnection(String resourceId) {
        DataSourceRecord dataSourceRecord = dataSourceRecordMapper.selectById(resourceId);
        if (dataSourceRecord == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        DataSourceRecordReq dataSourceRecordReq = BeanUtils.copy(dataSourceRecord, DataSourceRecordReq.class);
        return testDBConnection(dataSourceRecordReq);
    }

    @Override
    public List<String> getDataSourceType() {
        return dataSourceTemplateMapper.getDataSourceType();
    }

    @Override
    public ListRsp<DataSourceRecordRes> getApiConnectPage(DataSourceRecordPageReq dataSourceRecordPageReq) {
        Page page = dataSourceRecordPageReq.toPageParam();
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
                CommonConstant.FALSE);
        return PageUtil.getListResult(this.getBaseMapper().selectPage(page,authContext,dataSourceRecordPageReq));
    }

    @Override
    public DataResourceRecordInfoRes getDataSourceRecordInfo(String resourceId) {
        if (StringUtils.isEmpty(resourceId)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        DataSourceRecord dataSourceRecord = dataSourceRecordMapper.selectById(resourceId);
        return BeanUtils.copy(dataSourceRecord, DataResourceRecordInfoRes.class);
    }

    @Override
    public Integer deleteDataSourceRecord(DataSourceRecordDelReq dataSourceRecordDelReq) {
        return dataSourceRecordMapper.deleteBatchIds(dataSourceRecordDelReq.getDataResourceIdList());
    }

    @Override
    public String createDataSourceRecord(DataSourceRecordReq dataSourceRecordReq) {
        DataSourceRecord dataSourceRecord = dataSourceRecordReqToDataSourceRecord(dataSourceRecordReq);
        dataSourceRecord.setResourceId(IDGeneratorUtil.getID());
        dataSourceRecord.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        dataSourceRecordMapper.insert(dataSourceRecord);
        return dataSourceRecord.getResourceId();
    }

    @Override
    public String updateDataSourceRecord(DataSourceRecordReq dataSourceRecordReq) {
        if (dataSourceRecordReq == null) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        String resourceId = dataSourceRecordReq.getResourceId();
        if (StringUtils.isEmpty(resourceId)) {
            throw new BusinessException(ErrorCode.METHOD_ARG_NOT_VALID);
        }
        DataSourceRecord dataSourceRecord = dataSourceRecordReqToDataSourceRecord(dataSourceRecordReq);
        DataSourceRecord dataSource = dataSourceRecordMapper.selectById(resourceId);
        if (dataSource == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        dataSourceRecordMapper.updateById(dataSourceRecord);
        return resourceId;
    }

    private void closeDB(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            rs = null;

            if (pstmt != null) {
                pstmt.close();
            }
            pstmt = null;

            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (Exception ee) {
            log.error("数据源关闭失败", ee);
        }
    }
    private DataSourceRecord dataSourceRecordReqToDataSourceRecord(DataSourceRecordReq dataSourceRecordReq){
        return BeanUtils.copy(dataSourceRecordReq, DataSourceRecord.class);
    }
}
