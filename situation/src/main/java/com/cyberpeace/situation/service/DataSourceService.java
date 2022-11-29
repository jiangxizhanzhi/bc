package com.cyberpeace.situation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.DataSourceRecordDelReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordPageReq;
import com.cyberpeace.situation.controller.req.DataSourceRecordReq;
import com.cyberpeace.situation.controller.res.DataResourceRecordInfoRes;
import com.cyberpeace.situation.controller.res.DataSourceRecordRes;

import java.util.List;

/**
 * @Author xuxz
 * @Date 28/10/2022 上午11:24
 */
public interface DataSourceService {

    /**
     * 数据库连接测试接口
     * @param dataSourceRecordReq
     * @return
     */
    String testDBConnection(DataSourceRecordReq dataSourceRecordReq);

    /**
     * 数据库连接测试接口
     * @param resourceId
     * @return
     */
    String testDBConnection(String resourceId);

    /**
     * 获取数据源类型
     * @return
     */
    List<String> getDataSourceType();

    /**
     * 分页查询数据源
     * @param dataSourceRecordPageReq
     * @return
     */
    ListRsp<DataSourceRecordRes> getApiConnectPage(DataSourceRecordPageReq dataSourceRecordPageReq);

    /**
     * 获取数据源详情
     * @param resourceId
     * @return
     */
    DataResourceRecordInfoRes getDataSourceRecordInfo(String resourceId);

    /**
     * 批量删除数据源
     * @param dataSourceRecordDelReq
     * @return
     */
    Integer deleteDataSourceRecord(DataSourceRecordDelReq dataSourceRecordDelReq);

    /**
     * 新增数据源
     * @param dataSourceRecordReq
     * @return
     */
    String createDataSourceRecord(DataSourceRecordReq dataSourceRecordReq);

    /**
     * 更新数据源
     * @param dataSourceRecordReq
     * @return
     */
    String updateDataSourceRecord(DataSourceRecordReq dataSourceRecordReq);

    /**
     * 获取数据库表信息
     * @param resourceId
     * @return
     */
    List<String> getDBTables(String resourceId, String dbName);

    /**
     * 获取数据库信息
     * @param resourceId
     * @return
     */
    List<String> getDBName(String resourceId);

    /**
     *
     * @param sql
     * @return
     */
    List<JSONObject> getDataSourceData(String sql, String resourceId, String dbName);
}
